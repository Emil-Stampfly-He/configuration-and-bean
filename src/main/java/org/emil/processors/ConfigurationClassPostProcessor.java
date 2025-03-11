package org.emil.processors;

import org.emil.annotations.Configuration;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * @author EmilSt
 *
 * 找到所有加了@Configuration的类
 */
public class ConfigurationClassPostProcessor {

    /**
     * 获取所有加了@Configuration的类对象
     * @return 存储了所有@Configuration类对象的集合
     */
    public static List<Class<?>> getClasses() {
        List<Class<?>> classes = new ArrayList<>();
        // 1. 限定扫描范围
        // 1.1 获取资源路径
        String packageName = "org.emil.config";
        String packagePath = packageName.replace(".", "/");
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            // 1.2 获取指定路径下所有资源URL
            Enumeration<URL> resources = classLoader.getResources(packagePath);

            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                String protocol = url.getProtocol();
                String decodedURL = URLDecoder.decode(url.getFile(), StandardCharsets.UTF_8);

                if ("file".equals(protocol)) {
                    // 协议为file，说明类文件位于文件系统中
                    File directory = new File(decodedURL);
                    if (directory.isDirectory()) {
                        File[] files = directory.listFiles();
                        if (files != null) {
                            // 2. 扫描指定包下所有类
                            for (File file : files) {
                                if (file.getName().endsWith(".class")) {
                                    // 3. 加载类并检查注解
                                    // 3.1 拿到类名
                                    String className = file.getName().substring(0, file.getName().length() - ".class".length());
                                    // 3.2 加载类对象
                                    Class<?> clazz = Class.forName(packageName + "." + className);

                                    // 3.3 检测该类是否被@Configuration注解标记
                                    if (clazz.isAnnotationPresent(Configuration.class)) {
                                        // 3.4 有注解，则将该Class对象添加到集合中
                                        classes.add(clazz);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return classes;
    }
}
