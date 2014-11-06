/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package startimport;

/**
 *
 * @author benjakul
 */
import java.io.*;
import java.util.ArrayList;


public class ChkFileExist{
  public static WriteText wt; 
  public static void main(String args[]){
  
  }
  
  
  public static String getFileName(String fileName) {
      
      File file = new File(fileName);
        boolean exists = file.exists();
        String textSent = "";
      if (!exists) {        
       textSent = "0#"+fileName;        
      }else{     
        textSent = "1#"+fileName;
      }     
      return textSent;
  }
  public static ArrayList<String> listFilesForFolder(File folder) {
        String fullFilePath = "";
        ArrayList<String> iVec = new ArrayList<String>();
            try{
                for (File fileEntry : folder.listFiles()) {
                    if (fileEntry.isDirectory()) {
                        listFilesForFolder(fileEntry);
                    } else {
                        fullFilePath = fileEntry.getName();     
                        iVec.add(fullFilePath);                               
                    }
                }
            } catch(NullPointerException e ){
                
            }            
            return iVec;
    }
  
}
