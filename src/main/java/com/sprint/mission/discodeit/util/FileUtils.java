//package com.sprint.mission.discodeit.util;
//
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.ObjectInputStream;
//import java.io.ObjectOutputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.util.LinkedHashSet;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class FileUtils {
//
//    public static void initDirectory(Path directory) {
//        if (!Files.exists(directory)) {
//            try {
//                Files.createDirectories(directory);
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//
//    public static <T> void save(Path filePath, T data) {
//        try (
//                FileOutputStream fos = new FileOutputStream(filePath.toFile());
//                ObjectOutputStream oos = new ObjectOutputStream(fos);
//        ) {
//            oos.writeObject(data);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static <T> Set<T> load(Path directory) {
//        Set<T> set = new LinkedHashSet<>();
//        if (Files.exists(directory)) {
//            try (Stream<Path> paths = Files.list(directory)){
//                set = paths
//                        .map(path -> {
//                            try (
//                                    FileInputStream fis = new FileInputStream(path.toFile());
//                                    ObjectInputStream ois = new ObjectInputStream(fis)
//                            ) {
//                                Object data = ois.readObject();
//                                return (T) data;
//                            } catch (IOException | ClassNotFoundException e) {
//                                throw new RuntimeException(e);
//                            }
//                        }).collect(Collectors.toCollection(LinkedHashSet::new));
//                return set;
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        } else {
//            return set;
//        }
//    }
//
//    public static void remove(Path directory) {
//        if (Files.exists(directory)) {
//            try {
//                Files.delete(directory);
//            } catch (IOException e) {
//                 e.printStackTrace();
//            }
//        }
//
//    }
//}
