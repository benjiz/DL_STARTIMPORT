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
import java.util.Date;


public class WriteText{
        
        
        public  ArrayList<String> textVector = new ArrayList<String>();        
	public static void main(String[] args){	
	}
        public void write (ArrayList<String> getALStr,String fileFolder){            
             writeTIS620(getALStr,fileFolder);
        } 
        public void write (ArrayList<String> getALStr,String fileFolder , String str){      
            if (str.equalsIgnoreCase("UTF8") || str.equalsIgnoreCase("UTF-8"))
             writeUTF8(getALStr,fileFolder);
        }
      
        
        public void writeLog (ArrayList<String> getLog,String logFolder)  {              
            String logName = logFolder+".txt";    
            writeTIS620(getLog,logName);
        }//
        
        public void writeBatch (ArrayList<String> getBatch,String batchFolder){
            String iFolder = batchFolder+ ".bat";
            ArrayList<String> newIVec = new ArrayList<String>();
            for (int i = 0 ; i < getBatch.size() ; i++){          
                    newIVec.add(getBatch.get(i).toString().replace("//","\\"));                     
               }
            writeTIS620(newIVec,iFolder); 
         }
        
         private void writeUTF8(ArrayList<String> getData,String fileFolder){
            
             
             try {  
                Writer  out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileFolder), "UTF-8"));
    
               for (int i = 0 ; i < getData.size() ; i++){
                    out.write(getData.get(i).toString());
                    out.write("\r\n");                   
               }
               out.close();
            }
            catch (Exception e){
            
            }
             
         }
         private void writeTIS620(ArrayList<String> getData,String fileFolder){                   
             try {  
                Writer  out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileFolder), "TIS-620"));
    
               for (int i = 0 ; i < getData.size() ; i++){
                    out.write(getData.get(i).toString());
                    out.write("\r\n");                   
               }
               out.close();
            }
            catch (Exception e){
            
            }
             
         }

}