package startimport;
/**
 *
 * @author benjakul
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;


public class StartImport {


    public static void main(String[] args) {
    ///chk argument            
        if (args.length > 0 ) {     
            chkCmdCase(args);
        }
    // if not start GUI
        else { 
            mainUI ui = new mainUI();
            ui.runUI();
        }
        
        
    }
    private static void chkCmdCase(String[] args){
        if (args.length < 1 ) {
            System.out.println("Missing Input value...");
        }
        else{
        for (int i = 0; i < args.length; i++) {
        System.out.println("args[" + i + "]: " + args[i]);
         }
        
        if (args[0].equalsIgnoreCase("step0")) {
            step0(args);
        }
        else if (args[0].equalsIgnoreCase("step3s1")) {
            step3s1(args);
        }
        else if (args[0].equalsIgnoreCase("step3s2")) {
            step3s2(args);
        }   
        else if (args[0].equalsIgnoreCase("step4")) {
            step4(args);
        }         
        else if (args[0].equalsIgnoreCase("step5")) {
            step5(args);
        }
        else if (args[0].equalsIgnoreCase("step1")) {
            step1(args);
        }
        else if (args[0].equalsIgnoreCase("step6add")) {
            step6add(args);          
        }
        else if (args[0].equalsIgnoreCase("step6del")) {
            step6del(args);
        }
        else if (args[0].equalsIgnoreCase("deleteItem")){
            deleteItem(args);
        }

        else {
            System.out.println("Missing Input Value ... Please try again");
        }
        }
    }
    private static void deleteItem(String[] args){
            //args1 = map file path
            //args2 = email deleter
            //args3 = batch destination
            String batchDest = args[3];
            String folder =" -m "+ args[1];
            String email = " -e "+args[2];
            System.out.println("Start Deleting Items");
            ArrayList<String> batchAL = new ArrayList<String>();
            batchAL.add("@echo off");
            batchAL.add("echo STAQ Technologies Deleting Script");
            batchAL.add("F:");
            batchAL.add("cd F:/dspace/bin");
            batchAL.add("dspace import -d "+email + folder);
            WriteText wt = new WriteText();
            wt.writeBatch(batchAL, batchDest);

    
    }
    private static void step0(String[] args){ 
            //args1 = collection handle
            //args2 = batch full file path
            System.out.println("Start Step0");
            String collection = args[1];
            String batchDest = args[2];
            ArrayList<String> batchAL = new ArrayList<String>();
            batchAL.add("@echo off");
            batchAL.add("echo STAQ Technologies Import Script");
            batchAL.add("F:");
            batchAL.add("cd F:/dspace/bin");
            batchAL.add("dspace filter-media -i "+collection +" -n");
            WriteText wt = new WriteText();
            wt.writeBatch(batchAL, batchDest);   
     
          
    }
    private static void step1(String[] args){
            //args1 = excel full path    
            //args2 = full path pdf directory
            //args3 = full path log file
            //args4 = full path batch file
            //args5 = full file for import folder
            System.out.println("Start Step1");
            String logFile = args[3];
            String batchFile = args[4];
            ReadExcelFile readEF = new ReadExcelFile(); 
            ArrayList<List<String>> iVec = readEF.ReadExcel(args[1]);            
            ArrayList<String> nVec=  readEF.getFileMiss(iVec, args[2],logFile);

            WriteText wt = new WriteText();

            wt.writeLog(nVec, logFile);
            
            nVec = splitLogForBatch(nVec,args[1],args[5]);
            wt.writeBatch(nVec, batchFile);
            
    }
    
    private static void step3s1(String[] args){
            //args1 = excel full path  
            //args2 = full path pdf directory
            //args3 = destination dump
            //args4 = batch full path folder
            System.out.println("Start Step3s1");
            String targetFolder = args[3];
            String batchDest = args[4];
            ReadExcelFile readEF = new ReadExcelFile(); 
            ArrayList<List<String>> iVec = readEF.ReadExcel(args[1]);
            ArrayList<String> nVec= readEF.getIdentifier(iVec);
            nVec = splitForDump(nVec,targetFolder,args[2]);
            WriteText wt = new WriteText();
            wt.writeBatch(nVec, batchDest);
    }
    
    private static void step3s2 (String[] args){
            //args1 = excel full path  
            //args2 = full path text dumped directory
            File folder = new File(args[2]);
            System.out.println("Start Step3s2");
            ReadExcelFile ref = new ReadExcelFile();                       
            ref.ReadForWriteExcel(args[1], folder);         
    }
    private static void step4 (String[] args){
            //args1 = excel full path
            //args2 = file for import diretory
            String folder = args[2];
            System.out.println("Start Step4");
            ReadExcelFile ref = new ReadExcelFile(); 
            ArrayList<List<String>> alStr = ref.ReadExcel(args[1]);
            ref.ReadForWriteXML(alStr, folder);            
    }
    private static void step5 (String[] args){
            //args1 = collection handle 
            //args2 = full import folder
            //args3 = email importer
            //args4 = batch destination folder
            //args5 = is workflow
            Date dt = new Date();
            String dateParse = dt.toString().replace(' ', '_');
            String dateParsed = dateParse.replace(':', '-');
            String[] dateSplit = dateParsed.split("_ICT_");
            String batchDest = args[4];
            String folder =" -s "+ args[2];
            String collection =" -c " + args[1];
            String email = " -e "+args[3];
            //String mapFile = " -m ("+args[1].replace("/","#") +")"+dateSplit[0]+"-"+dateSplit[1]+"("+args[3]+")";
            String dynamicMap = " -m "+args[6];
            System.out.println("Start Step5");
            String isWorkflow = "";
            if (args[5].equalsIgnoreCase("true")){
                isWorkflow = " -w ";
            }
            ArrayList<String> batchAL = new ArrayList<String>();
            batchAL.add("@echo off");
            batchAL.add("echo STAQ Technologies Import Script");
            batchAL.add("F:");
            batchAL.add("cd F:/dspace/bin");
            batchAL.add("dspace import -a "+isWorkflow+email + folder + collection + dynamicMap/* mapFile*/);
            WriteText wt = new WriteText();
            wt.writeBatch(batchAL, batchDest);                      
    }
    
    private static void step6add(String[] args)
    {
            //args1 = full import folder
            //args2 = email importer
            //args3 = batch destination folder
            String batchDest = args[3];
            String folder =" -s "+ args[2];
            String email = " -e "+args[1];
            System.out.println("Start Step6add");
            ArrayList<String> batchAL = new ArrayList<String>();
            batchAL.add("@echo off");
            batchAL.add("echo STAQ Technologies Import Script");
            batchAL.add("F:");
            batchAL.add("cd F:/dspace/bin");
            batchAL.add("dspace itemupdate -A dc.identifier.uri "+email + folder);
            WriteText wt = new WriteText();
            wt.writeBatch(batchAL, batchDest);
    }
    private static void step6del(String[] args)
    {
            //args1 = full import folder
            //args2 = email importer
            //args3 = batch destination folder
            String batchDest = args[3];
            String folder =" -s "+ args[2];
            String email = " -e "+args[1];
            System.out.println("Start Step6del");
            ArrayList<String> batchAL = new ArrayList<String>();
            batchAL.add("@echo off");
            batchAL.add("echo STAQ Technologies Import Script");
            batchAL.add("F:");
            batchAL.add("cd F:/dspace/bin");
            batchAL.add("dspace itemupdate -D "+email + folder);
            WriteText wt = new WriteText();
            wt.writeBatch(batchAL, batchDest);
    }
    private static ArrayList<String> splitLogForBatch(ArrayList<String> iVec,String iFileName,String ffi){
        ArrayList<String> batchVec = new ArrayList<String>();
        String[] chkResult;
        String[] lastSlash;
        String tempText = "";
        String dest ="";
        batchVec.add("@echo off");
        batchVec.add("echo STAQ Technologies Initial PDF Script");
        
        for (int i = 0 ; i < iVec.size() ; i++){       
            chkResult = iVec.get(i).toString().split("#");           
            lastSlash = chkResult[1].split("/");
        
            if (chkResult[0].equalsIgnoreCase("1")){
                    dest = ffi+"\\"+chkResult[2];
                    tempText= "mkdir "+dest;
                    batchVec.add(tempText);
                    tempText="echo f | xcopy "+chkResult[1]+" "+dest+"\\"+lastSlash[lastSlash.length-1] + " /y" ;
                    batchVec.add(tempText);                
            }                                                         
        }                
        return batchVec;       
    }
    private static ArrayList<String> splitForDump (ArrayList<String> iVec,String iDestFolder,String iPdfFolder){
        ArrayList<String> batchVec = new ArrayList<String>();
        String[] splitSharp;
        String tempText = "";
        String iFileName ="";
        batchVec.add("@echo off");
        batchVec.add("echo STAQ Technologies Dumping Bookmark Script"); 
        //batchVec.add("mkdir " + iDestFolder);
        
         for (int i = 0 ; i < iVec.size() ; i++){    
             splitSharp = iVec.get(i).toString().split("#");
             iFileName = splitSharp[0];             
             tempText = "C:\\STAQ\\jpdfbookmarks\\jpdfbookmarks_cli -i \"<child>\" -p \"<page>\" "+iPdfFolder+"\\"+iFileName+" -d -e TIS-620 -o "+iDestFolder+"\\"+iFileName+".txt -f";
             batchVec.add(tempText);
        }    
        return batchVec;
    }
   
}


