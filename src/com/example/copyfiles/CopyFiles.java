package com.example.copyfiles;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CopyFiles {

    public static void main(String[] args) {

        System.out.println("> This is a project to copy files");

        String[] listExtension = {"mp3"};

        File sourceLocation = new File("C:\\Users\\jclavota\\Desktop\\JoseTestSource");
        File targetLocation = new File("D:\\JoseTestTarget");

        if (!sourceLocation.isDirectory())
        {
            System.out.println("Doesn't exist directory: "+sourceLocation);
            System.exit(0);
        }


        File[] filesSource = sourceLocation.listFiles();
        filesSource = sourceLocation.listFiles();

        if(filesSource.length == 0)
        {
            System.out.println("There aren't files in directory: "+sourceLocation);
            System.exit(0);
        }

        if (!targetLocation.exists())
        {
            System.out.println("Connect your device, please.");
            System.exit(0);
        }

        File[] filesTarget = targetLocation.listFiles();

        filesSource = filterListField(filesSource,listExtension);
        filesTarget = filterListField(filesTarget,listExtension);

        if(filesSource.length == 0)
        {
            System.out.println("There are not files (with the extension selected) to transfer.");
            System.exit(0);
        }

        filesSource = compareListField(filesSource,filesTarget);

        if(filesSource.length == 0)
        {
            System.out.println("There are not new files to transfer.");
            System.exit(0);
        }

        try {
            for (File file : filesSource) {

                InputStream in = new FileInputStream(file);

                OutputStream out = new FileOutputStream(targetLocation + "/" + file.getName());

                // Copy the bits from input stream to output stream
                byte[] buf = new byte[1024];
                int len;
                try {
                    while ((len = in.read(buf)) > 0) {
                        out.write(buf, 0, len);
                    }
                    in.close();
                    out.close();
                } catch (IOException e) {
                    //e.printStackTrace();
                    System.out.println("File: "+file.getName()+", Estado : No Transfer completed");
                }
                finally {
                    System.out.println("File: "+file.getName()+", Estado : Transfer completed");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static String getFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        if (i >= 0) {
            extension = fileName.substring(i+1);
        }

        return extension;
    }


    private static File[] filterListField(File[] filesList, String[] extensionList)
    {
        List<File> ListFilesFilters = new ArrayList<File>();

        String fileExtension;

        for (File file : filesList) {

            if (!file.isDirectory()) {

                fileExtension = getFileExtension(file.getName());

                for(String extension: extensionList )
                {
                    if (fileExtension.equals(extension)) {
                        ListFilesFilters.add(file);
                        break;
                    }
                }
            }
        }

        File[] filesFilters = changeListToFile(ListFilesFilters);

        return filesFilters;
    }

    private static File[] compareListField(File[] filesSource, File[] filesTarget)
    {

        List<File> ListFilesFilters = new ArrayList<File>();
        String nameFieldSource;
        int flagMatchFiles = 0;

        if(filesTarget.length > 0)
        {
            for (File fileSource : filesSource) {

                nameFieldSource = fileSource.getName();
                flagMatchFiles = 0;

                for (File fileTarget : filesTarget) {
                    if(nameFieldSource.equals(fileTarget.getName()))
                    {
                        flagMatchFiles = 1;
                        break;
                    }
                }

                if(flagMatchFiles == 0)
                {
                    ListFilesFilters.add(fileSource);
                }
            }

            File[] filesFilters = changeListToFile(ListFilesFilters);

            return filesFilters;
        }

        return filesSource;
    }

    private static File[] changeListToFile(List<File> ListFile)
    {
        int sizeList;
        int counter;

        sizeList = ListFile.size();
        File[] filesFilters = new File[sizeList];
        counter = 0;
        while (counter < sizeList) {
            filesFilters[counter ] = ListFile.get(counter);
            counter ++;
        }

        return filesFilters;
    }

}
