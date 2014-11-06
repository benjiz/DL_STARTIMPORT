/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package startimport;

import java.io.*;
import java.util.Vector;


/**
 *
 * @author benjakul
 */
public class ReadTextFile {
    public static String readTextLine(String iFileName){
        String concatStr = "";
        String str = "";
               try {
                    BufferedReader in = new BufferedReader(new FileReader(iFileName));                    
                        while ((str = in.readLine()) != null) {                             
                        }
                    in.close();
                } 
                catch (Exception e) {

                }                        
                           
        return concatStr;
    }
    
   
}
