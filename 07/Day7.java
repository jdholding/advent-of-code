import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day7 {
    // line == "$ ls" everything until next $ is the contents of that directory
    public static void main(String[] args){
        List<String> allLines;
        try{
            // Scanner sc = new Scanner(System.in);
            allLines = Files.readAllLines(Paths.get("input.txt"));
        } catch(IOException e){
            e.printStackTrace();
            return; 
        }
        Directory fileSystem = makeFileSystem(allLines); 


        Integer total = fileSystem.getTotalSmallDirs();

        System.out.println("The total size of directories under 100000 is: " + total);

        System.out.println("The total size of the filesystem is: "+ fileSystem.getSize());
        System.out.println("Need to free up: "+ (70000000 - fileSystem.getSize()));
        
        Integer diskTotal = 70000000;
        Integer diskRequired = 30000000;
        Integer diskAvailable = diskTotal - fileSystem.getSize();
        var toFree = 19783544; 
        HashMap<Directory,Integer> sizeMap = new HashMap<>(); 
        addToSizeMap(fileSystem, sizeMap);
        List<Integer> dirSizes = new ArrayList<Integer>(); 
        for(Map.Entry<Directory,Integer> entry: sizeMap.entrySet()){
            dirSizes.add(entry.getValue());
        } 
        dirSizes.sort(Comparator.naturalOrder());
        for(Integer size: dirSizes){
            System.out.println(size);
        }
        for(Integer size: dirSizes){
            if(size + toFree >= diskRequired){
                System.out.println("The answer to part 2 is: " + size);
                // 50216456 too high
                break;
            }
        }
        // printFilesystem(fileSystem); 
        // Integer total2 = 0;
        // for(Map.Entry<Directory,Integer> entry: sizeMap.entrySet()){
        // if(entry.getValue() <= 100000){
        //         System.out.println("The folder: " + entry.getKey() + " has the size: " + entry.getValue());
        //         total2 += entry.getValue();  
        // } 
        // // if (findSubDirectoryByName(fileSystem, entry.getKey()) != null) {
        // //     System.out.println("size of " + entry.getKey() + " according to sizeMap: " + entry.getValue());
        // //     System.out.println("size of " + entry.getKey() + " according to new method: " + findSubDirectoryByName(fileSystem, entry.getKey()).getSize());
        // // }
        // }
        System.out.println("The answer to part 1 is: " + total);
        // System.out.println(total2);
        // System.out.println(fileSystem);
        // 946812 is too low
        // 1080110 is too low
    }

    public static void addToSizeMap(Directory directory, HashMap<Directory,Integer> sizeMap){
        sizeMap.put(directory, directory.getSize());
            for(Directory subDir : directory.getDirectories()) {
                addToSizeMap(subDir, sizeMap);
            }
    }

    // calculates the total size of a directory including all subdirectories
    public static Integer calculateDirectorySize(Directory directory){
        Integer size = totalRootFiles(directory);
        for(Directory subDir : directory.getDirectories()){
            size += calculateDirectorySize(subDir);      
        }
        return size;
    }

    public static Integer totalRootFiles(Directory dir){
        Integer size = 0; 
        for(ElfFile file : dir.getFiles()){
            size += file.getSize(); 
        }
        return size; 
    }

    public static Directory makeFileSystem(List<String> allLines){
        Directory root = new Directory("/"); 
        Directory currentDir = root;
        // ignore the first line "cd /"
        var i = 1;
        while (i < allLines.size()){
            String line = allLines.get(i).trim(); 

            if(line.startsWith("$ cd ")){
                String destination = line.substring(5);
                currentDir = changeDirectory(currentDir, destination); 
                i = i + 1;
            }

            if(line.startsWith("$ ls")){
                i = i + 1;
                while (i < allLines.size() && !allLines.get(i).startsWith("$")) {
                    boolean didSomething = false;
                    var nextLine = allLines.get(i).trim(); 
                    if(nextLine.startsWith("dir")){
                        var dirName = allLines.get(i).substring(4);
                        var newDir = new Directory(dirName, currentDir);
                        currentDir.getDirectories().add(newDir);
                        didSomething = true;
                    }
                    if(Character.isDigit(nextLine.charAt(0))){
                        String[] subs = nextLine.split(" ");
                        var newFile = new ElfFile(subs[1], Integer.parseInt(subs[0]));
                        currentDir.getFiles().add(newFile);
                        didSomething = true;
                    } 
                    if (!didSomething) System.out.println("Did not do anything on line " + allLines.get(i));
                    i = i + 1;
                }

            }
        }
        return root; 
    }

    public static Directory changeDirectory(Directory currentDirectory, String destination){
        if(destination.equals("..")){
            if (currentDirectory.name.equals("/")) System.out.println("TRIED TO CD .. FROM ROOT");
            return currentDirectory.getParent(); 
        }
        return findSubDirectoryByName(currentDirectory, destination); 
    }

    
    // public static void addToNames(List<String> names, Directory dir){
    //     names.add(dir.getName());
    //     if(!dir.getDirectories().isEmpty()){
    //         for(Directory subDir: dir.getDirectories()){
    //             addToNames(names, subDir);
    //         }
    //     }
    // }
    // public static boolean containsDirectoryName(Directory dir, String name){
    //     List<String> names = new ArrayList();
    //     addToNames(names,  dir);
    //     return names.contains(name); 
    // }

    public static Directory findSubDirectoryByName(Directory dir, String name){
        for (Directory subdir : dir.getDirectories()) {
            if (subdir.getName().equals(name)) return subdir;
        }
        //System.out.println("Looking for directory " + name + " inside " + dir.getName() + " but did not find.");
        return null;
        // if(dir.getName().equals(name)) { 
        //     return dir; 
        // }
        // if(!dir.getDirectories().isEmpty()){
        //     for(Directory d : dir.getDirectories()){
        //         if(findSubDirectoryByName(d, name) != null){
        //             return findSubDirectoryByName(d, name);
        //         } else {
        //             continue;
        //         }
                
        //     }
        // } 
        // return null;
    }

    // public static Directory findParentDirectory(Directory dir, String parent){
    //     if(dir.getParent().equals(parent)) { 
    //         return dir; 
    //     }
    //     if(!dir.getDirectories().isEmpty()){
    //         for(Directory d : dir.getDirectories()){
    //             if(findParentDirectory(d, parent) != null){
    //                 return findParentDirectory(d, parent);
    //             } else {
    //                 continue;
    //             }
                
    //         }
    //     } 
    //     return null;
    // }

    public static class ElfFile{
        String name; 
        Integer size; 

        public ElfFile(String name, Integer size){
            this.name = name; 
            this.size = size; 
        }

        public String getName(){ return this.name; }

        public Integer getSize(){ return this.size; }
    }

    public static class Directory{
        String name;
        Directory parent;  
        List<Directory> directories; 
        List<ElfFile> files; 

        public Directory(String name){
            this.name = name;
            this.parent = null; 
            this.directories = new ArrayList<Directory>(); 
            this.files = new ArrayList<ElfFile>(); 
        }
        public Directory(String name, Directory parent){
            this.name = name;
            this.parent = parent; 
            this.directories = new ArrayList<Directory>(); 
            this.files = new ArrayList<ElfFile>();  
        }

        public String getName() { return this.name; }
        public void setName(String name) { this.name = name; }

        public Directory getParent() { return this.parent; }
        public void setParent(Directory parent) { this.parent = parent; }

        public List<Directory> getDirectories(){ return this.directories; }
        public void setDirectories(List<Directory> dirs){ this.directories = dirs; }

        public List<ElfFile> getFiles() { return this.files; }
        public void setFiles(List<ElfFile> files) { this.files = files; }

        public String toString() {
            String r = this.name + "\n-";
            for (Directory d : this.directories) {
                r += "-" + d.toString() + "\n";
            }
            for (ElfFile f : this.files) {
                r += "-" + f.getName() + "\n";
            }
            return r;
        }
        public Integer getSize() {
            Integer fileSizes = 0;
            for (ElfFile f : this.files) { fileSizes += f.getSize(); }
            Integer subSizes = 0;
            for (Directory d : this.directories) { subSizes += d.getSize(); }
            return fileSizes + subSizes;
        }

        // sum of sizes of directories under 100000 under this directory
        public Integer getTotalSmallDirs() {
            Integer total = 0;
            for (Directory d : this.directories) {
                total += d.getTotalSmallDirs();
            }
            if (this.getSize() <= 100000) { total += this.getSize(); }
            return total;
        }
    } 

    public static void printFilesystem(Directory fileSystem){
        Integer fileCount = 0;
        System.out.println(fileSystem.getName());
        for(ElfFile file : fileSystem.getFiles()){
            System.out.println("- (file " + file.getName() + " " + file.getSize());
            fileCount += file.getSize(); 
        }
        for(Directory directory : fileSystem.getDirectories()){ 
            System.out.println("(dir)"+directory.getName());
            for(ElfFile file: directory.getFiles()){
                System.out.println("  - (file) "+ file.getName() + " " + file.getSize());
                fileCount += file.getSize(); 
            }
            for(Directory dir: directory.getDirectories()){
                System.out.println("  (dir) "+ dir.getName());
                for(ElfFile file: dir.getFiles()){
                    System.out.println("    - (file) "+ file.getName() + " " + file.getSize());
                    fileCount += file.getSize(); 
                }
                for(Directory subDir : dir.getDirectories()){
                    System.out.println("    (dir) "+ subDir.getName());
                    for(ElfFile file: subDir.getFiles()){
                        System.out.println("      - (file) "+ file.getName() + " " + file.getSize());
                        fileCount += file.getSize(); 
                    }
                    for(Directory dir1: subDir.getDirectories()){
                        System.out.println("      (dir) "+ dir1.getName());
                        for(ElfFile file: dir1.getFiles()){
                            System.out.println("        - (file) "+ file.getName() + " " + file.getSize());
                            fileCount += file.getSize(); 
                        }
                        for(Directory subDir1 : dir1.getDirectories()){
                            System.out.println("        (dir) "+ subDir1.getName());
                            for(ElfFile file: subDir1.getFiles()){
                                System.out.println("          - (file) "+ file.getName() + " " + file.getSize());
                                fileCount += file.getSize(); 
                            }
                        }
                    }
                }
            }

        }
        System.out.println("Sum of all file sizes: "+fileCount); 
    }
}