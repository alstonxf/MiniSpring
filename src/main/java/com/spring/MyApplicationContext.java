package com.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linjunzhen
 * @version 1.0
 * @date 2022/4/11 14:52
 */
public class MyApplicationContext {

    /**
     *     beanDefinitionMap:
     *     singletonObjects:
     *     beanPostProcessorList:
     *     这些属性在应用程序上下文的生命周期中会被使用，以支持Bean的依赖注入和生命周期管理。
     * 值得注意的是这里使用了ConcurrentHashMap
     * ConcurrentHashMap是Java中的一个线程安全的哈希表，它是HashMap的一种线程安全的实现。与HashMap不同，ConcurrentHashMap使用分段锁机制来保证并发访问的线程安全性，这样可以避免对整个哈希表加锁，从而提高并发访问的效率。
     * ConcurrentHashMap的主要优点是提高了并发读取数据的效率，而不会导致死锁等问题。它可以被多个线程同时访问和修改，而不会导致线程安全问题。同时，它也支持高并发的插入和删除操作。
     * 在多线程环境下，如果需要保证数据的线程安全性，就可以使用ConcurrentHashMap来存储数据。
     */
    private Class configClass;

    //存储Bean名称和对应的BeanDefinition的ConcurrentHashMap，用于保存应用程序中所有的Bean定义。
    private ConcurrentHashMap<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    //存储单例Bean对象的ConcurrentHashMap，用于保存已经实例化的单例Bean对象。
    private ConcurrentHashMap<String,Object> singletonObjects = new ConcurrentHashMap<>();
    //存储BeanPostProcessor对象的ArrayList，用于保存所有的BeanPostProcessor对象。以供AOP切面编程使用。
    private ArrayList<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    //该代码为Spring框架的ApplicationContext实现代码。该方法接收一个configClass参数，通过扫描指定路径下的
    public MyApplicationContext(Class configClass) {

        //step1：
        this.configClass = configClass;
        // 扫描 --->BeanDefinition -->beanDefinitionMap
        if (configClass.isAnnotationPresent(ComPonentScan.class)) {//判断传入的configClass的注解中是否有ComPonentScan
            //从传入的configClass中获取ComPonentScan注解对象。如果configClass类上没有ComPonentScan注解，那么componentScanAnnotation将为null。如果有ComPonentScan注解，componentScanAnnotation将保存该注解对象，我们可以通过这个对象获取注解的属性值。
            ComPonentScan componentScanAnnotation = (ComPonentScan) configClass.getAnnotation(ComPonentScan.class);
            String path = componentScanAnnotation.value();// 扫描路径
            System.out.println("扫描路径：" + path);
            path = path.replace(".", "/"); // 将扫描路径中的"."替换为"/"，方便下一步扫描

            //通过MyApplicationContext类的类加载器ClassLoader加载指定路径下的资源，并通过getResource()方法获取路径下的资源的URL对象，最后通过File对象来访问指定路径下的文件。这段代码的作用是获取指定路径下的文件夹，并获取该文件夹下所有的.class文件的绝对路径，以便之后进行类的加载和实例化。
            //一般来说，推荐使用当前类的类加载器或线程上下文类加载器来加载资源。例如，使用 Thread.currentThread().getContextClassLoader() 来获取线程上下文类加载器。这种方式可以避免因类加载器不一致而导致的资源加载错误。
            ClassLoader classLoader = MyApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path); // 获取指定路径下的资源
            File file = new File(resource.getFile());//File 是 Java 标准库中用来表示文件或目录的类，它提供了对文件和目录进行创建、读取、写入、删除等操作的方法。因此 files 是一个 File 类型的数组。在循环中遍历该数组，并对每一个 File 对象进行处理。

            //过滤.class 文件的路径。
            int i = 1;
            if (file.isDirectory()) { // 如果是文件夹，则遍历文件夹下的所有文件
                File[] files = file.listFiles();//listFiles() 方法返回该目录下的文件和子目录的数组
                for (File f : files) {
                    String fileName = f.getAbsolutePath(); // 获取文件路径
                    if (fileName.endsWith(".class")) { // 如果是class文件，则进行下一步操作
                        System.out.println(i+".1 路径下有class后缀的文件 fileName：" + fileName);
                        //获取到传入 MyApplicationContext 构造方法的 configClass 参数所在的包名，从而避免写死包名。
                        String packageName = configClass.getPackage().getName();
                        String packagePath = packageName.replace(".", "/");
                        String className = fileName.substring(fileName.indexOf(packagePath), fileName.indexOf(".class"));
                        System.out.println(i+".2 包路径className：" + className);
                        className = className.replace("\\", ".").replace("/","."); // 将路径中的"\\"替换为"."，方便反射调用
                        System.out.println(i+".3 将路径中的\\替换为.方便反射调用:"+className);
                        try {
                            Class<?> clazz = classLoader.loadClass(className); // 通过类加载器加载类,还没有实例化
                            if (clazz.isAnnotationPresent(Component.class)) { // 如果是组件，则进行下一步操作
                                if (BeanPostProcessor.class.isAssignableFrom(clazz)) { //如果该类实现了 BeanPostProcessor 接口，就将它实例化后加入 beanPostProcessorList 列表中。
                                    BeanPostProcessor instance = (BeanPostProcessor) clazz.newInstance();
                                    beanPostProcessorList.add(instance);
                                }

                                Component component = clazz.getAnnotation(Component.class);
                                String beanName = component.value();//拿到component注解的值，也就是设定的beanName

                                if ("".equals(beanName)) { // 如果组件未指定beanName，则使用类名的小写作为beanName
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }

                                // BeanDefinition
                                BeanDefinition beanDefinition = new BeanDefinition(); // 创建BeanDefinition对象
                                if (clazz.isAnnotationPresent(Scope.class)) { // 如果组件有Scope注解，则设置beanDefinition的scope属性
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else { // 如果没有Scope注解，则默认为singleton
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinition.setType(clazz);
                                beanDefinitionMap.put(beanName, beanDefinition); // 将beanDefinition对象加入beanDefinitionMap中
                            }
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                    i+=1;
                }
            }
        }

        // 实例化单例bean
        for (String beanName : beanDefinitionMap.keySet()) { // 遍历beanDefinitionMap中的所有beanDefinition对象
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) { // 如果bean的scope为singleton，则进行实例化操作
                Object bean = createBean(beanName, beanDefinition); // 调用createBean方法实例化bean
                singletonObjects.put(beanName, bean); // 将bean加入singletonObjects中
            }
        }
    }


    /**
     *
     * @param beanName
     * @param beanDefinition
     * @return
     * 这段代码是一个简单的 Bean 工厂中创建 Bean 的方法实现。根据传入的 beanName 和 beanDefinition 参数创建一个新的实例，同时进行一些初始化操作。
     *
     * 具体来说，这段代码的主要功能包括：
     *
     * 根据 beanDefinition 获取要创建的 Bean 的类型（即 Class 对象）。
     * 使用反射创建该类型的实例对象。
     * 遍历该类型中所有的成员变量，如果某个成员变量标记了 @Autowired 注解，就将该成员变量设置为对应的 Bean 实例。
     * 如果该类型实现了 BeanNameAware 接口，就调用 setBeanName() 方法，将 beanName 传入，以便该 Bean 实例获取自己的 Bean 名称。
     * 对该 Bean 实例进行 BeanPost 处理。BeanPostProcessor 是 Spring 框架中的一个扩展点，用于在 Bean 实例创建和初始化过程中对 Bean 实例进行特定的处理。这里分别调用 BeanPostProcessor 的 postProcessBeforeInitialization() 和 postProcessAfterInitialization() 方法，对 Bean 实例进行前置和后置处理。
     * 如果该类型实现了 InitializingBean 接口，就调用 afterPropertiesSet() 方法，完成 Bean 的初始化操作。
     * 返回该 Bean 实例对象。
     * 总之，这段代码的作用是通过反射创建一个 Bean 实例，并对该 Bean 实例进行必要的初始化和处理，最终返回该 Bean 实例。这是实现一个简单的 Bean 工厂的关键步骤之一。
     */
    private Object createBean(String beanName,BeanDefinition beanDefinition){
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getConstructor().newInstance();//简单的直接通过默认无参构造器实例化。todo 添加参数构造器实例化逻辑
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);
//                    f.set(instance, ...) 方法是反射中的一种设置成员变量值的方式。其中，f 表示要设置的成员变量对象，instance 是要设置的成员变量所在的对象，而 ... 则是要设置的值。
//                    具体来说，这个方法的作用是将一个对象的某个成员变量设置为指定的值。其中，第一个参数 instance 是要设置值的对象，第二个参数则是要设置的值。因为反射可以访问和修改类的私有成员变量，所以即使被设置的成员变量是私有的，也能够成功地将其设置为指定的值。
//                    需要注意的是，由于这个方法是在运行时动态修改对象的成员变量，因此其效率不如直接访问成员变量。但是，通过反射机制，我们可以动态地获取和设置对象的成员变量，从而实现更加灵活的程序设计。
//                    总之，f.set(instance, ...) 方法是反射中一种设置成员变量值的方式，其作用是将一个对象的某个成员变量设置为指定的值。
                    f.set(instance,getBean(f.getName()));

//                    另一种方法，可以使用反射中的 Method 类中的 invoke() 方法来替代 f.set(instance, ...) 的写法。具体来说，可以在循环中获取需要自动装配的属性的 Setter 方法（例如，属性名为 foo，则对应的 Setter 方法名为 setFoo）并调用该方法设置属性值，代码如下：
//                    该代码中，首先获取属性名和对应的 Setter 方法名，并使用 clazz.getMethod() 方法获取 Setter 方法对象。然后，使用 invoke() 方法调用 Setter 方法，传入 instance 对象和需要设置的属性值，即 getBean(fieldName)。这样就完成了自动装配属性的操作，同时也避免了直接访问属性的一些问题。
//                    String fieldName = f.getName();
//                    Method setterMethod = clazz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), f.getType());//要确保有set方法
//                    setterMethod.invoke(instance, getBean(fieldName));

                }
            }

            // Aware回调 不影响
//            这段代码是在Spring框架中的Bean实例化过程中的一个回调方法，用于在Bean实例化完成之后，给Bean对象设置Bean名称。
//            具体地，该代码是判断当前Bean实例对象是否实现了BeanNameAware接口，如果是，则将当前Bean的名称设置到BeanNameAware实例对象中。
//            BeanNameAware接口中只有一个方法：setBeanName(String name)，用于设置当前Bean的名称。
//            这个回调方法不会影响Bean的生命周期，只是在Bean实例化完成之后，给Bean对象设置Bean名称。在Spring容器中，Bean的名称是很重要的一个属性，通过Bean的名称，可以在容器中获取到对应的Bean实例对象。
            if (instance instanceof BeanNameAware) {//检查当前 Bean 实例是否实现了 BeanNameAware 接口；
                ((BeanNameAware) instance).setBeanName(beanName);//如果实现了，就将当前 Bean 的名字注入进去。
            }

            // BeanPost
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(beanName,instance);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // BeanPost
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(beanName,instance);
            }
            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 该方法是一个根据bean名称获取对应bean实例的方法，方法返回一个Object类型的实例，即获取到的bean实例。
     * 方法内部先通过bean名称获取该bean的BeanDefinition对象，如果获取不到，则抛出一个空指针异常。
     * 如果获取到了BeanDefinition对象，接下来根据该对象的scope属性判断是singleton还是prototype，
     * 如果是singleton，则先尝试从singletonObjects缓存中获取该bean实例，如果获取不到，则调用createBean方法创建一个新的实例，并将其存入缓存中，然后返回该实例。
     * 如果是prototype，则直接调用createBean方法创建新的实例，然后返回该实例。
     * @param beanName
     * @return
     */
    public Object getBean(String beanName){
        // 获取指定名称的BeanDefinition对象
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        // 如果对象不存在，则抛出空指针异常
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            // 获取bean的作用域
            String scope = beanDefinition.getScope();
            // 如果是单例模式
            if ("singleton".equals(scope)) {
                // 从单例对象缓存中获取bean对象
                Object bean= singletonObjects.get(beanName);
                // 如果对象不存在，则创建bean对象并加入缓存中
                if (bean == null) {
                    bean = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName,bean);
                }
                // 返回bean对象
                return bean;
            } else {
                // 多例模式直接创建并返回bean对象
                return createBean(beanName, beanDefinition);
            }
        }
    }

}
