
package startimport;

/**
 *
 * @author benjakul
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;

public class ReadExcelFile {

        public static HSSFCell myCell;
        public static ChkFileExist cfe;
        
        public static void main(String[] args) {
                
        }
        public static int getSheetsSize(String fileName){
            int count = 0;
            try{
            FileInputStream myInput = new FileInputStream(fileName);  
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
            //HSSFSheet mySheet = myWorkBook.getSheetAt(0);
            count = myWorkBook.getNumberOfSheets();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return count;
        }
        
        public static ArrayList<List<String>> ReadExcel(String fileName) {
                System.out.println(fileName);
                ArrayList<List<String>> cellVectorHolder = new ArrayList<List<String>>();
                try {
                        FileInputStream myInput = new FileInputStream(fileName);  
              
                        POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                       
                        HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                        HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                        Iterator rowIter = mySheet.rowIterator();                       
                        HSSFRow myRow;
                  
                        Iterator cellIter;
                        ArrayList<String> cellStoreVector;
                        HSSFCell myCell;
                        int row = 0;
                        int col = 0;

                        boolean forceFin = false;
                        while (rowIter.hasNext() && !forceFin) {
                                myRow = (HSSFRow) rowIter.next();
                                cellIter = myRow.cellIterator();
                                cellStoreVector = new ArrayList<String>();

                                while (cellIter.hasNext() && !forceFin ) {
                                        myCell = (HSSFCell) cellIter.next();
                                        
                                  
                                            /*if(myCell.getCellType() == myCell.CELL_TYPE_BLANK)
                                            {
                                            //cellStoreVector.add("-".toString());
                                            cellStoreVector.clear();
                                            forceFin = true;
                                            }*/
                                            if(myCell.getCellType() == myCell.CELL_TYPE_NUMERIC)
                                            {
                                                if (DateUtil.isCellDateFormatted(myCell)){
                                                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                                    Date date = myCell.getDateCellValue();
                                                    String val = df.format(date);
                                                    cellStoreVector.add(val);
                                                }
                                                else{
                                                    int b = (int)myCell.getNumericCellValue();
                                                    cellStoreVector.add(String.valueOf(b));
                                                }
                                            }
                                            else
                                            {
                                                cellStoreVector.add(myCell.toString());
                                            }
                                        
                                        
                                        //cellStoreVector.add(myCell.toString());
                                         //System.out.print(cellStoreVector.get(col) +"["+row+","+col+"],");
                                         //col++;
                                }
                                if(!cellStoreVector.isEmpty()){
                                cellVectorHolder.add(cellStoreVector);
                                }
                        }
                        

                } catch (Exception e) {
                       e.printStackTrace();
                }
                return cellVectorHolder;
        }
        public static ArrayList<List<String>> ReadExcel(String fileName,int sheetNo) {
                System.out.println(fileName);
                ArrayList<List<String>> cellVectorHolder = new ArrayList<List<String>>();
                try {
                        FileInputStream myInput = new FileInputStream(fileName);  
              
                        POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                       
                        HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                        HSSFSheet mySheet = myWorkBook.getSheetAt(sheetNo);
                        Iterator rowIter = mySheet.rowIterator();                       
                        HSSFRow myRow;
                  
                        Iterator cellIter;
                        ArrayList<String> cellStoreVector;
                        HSSFCell myCell;
                    
                        while (rowIter.hasNext()) {
                                myRow = (HSSFRow) rowIter.next();
                                cellIter = myRow.cellIterator();
                                cellStoreVector = new ArrayList<String>();
                                while (cellIter.hasNext() ) {
                                        myCell = (HSSFCell) cellIter.next();
                                        
                                        if(myCell.getCellType() == myCell.CELL_TYPE_NUMERIC) { 
                                        int b = (int)myCell.getNumericCellValue(); 
                                        cellStoreVector.add(String.valueOf(b)); 
                               
                                        } else { 
                                        cellStoreVector.add(myCell.toString());
                                   
                                        }
                                        
                                        //cellStoreVector.add(myCell.toString());
                                }
                                cellVectorHolder.add(cellStoreVector);
                        }
                } catch (Exception e) {
                       e.printStackTrace();
                }
                return cellVectorHolder;
        }
        public static ArrayList<String> chkBibNo(ArrayList<List<String>> dataHolder){        
                boolean foundFileCol =false;
                ArrayList<String> getBib = new ArrayList<String>();
                String stringCellValue = "";
                ArrayList<String> cellStoreVector = new ArrayList<String>();  
                Integer hasBibNo = -1 ;
                Integer countCol = 0;
                for (int i = 0; i < dataHolder.size(); i++) {
                        cellStoreVector = (ArrayList<String>) dataHolder.get(i);
                        countCol = cellStoreVector.size();
  
                        for (int j = 0; j < countCol; j++) {
                                stringCellValue = cellStoreVector.get(j);                                
                                if(stringCellValue.equalsIgnoreCase("BibNo_Result")){                      
                                    hasBibNo = j ;
                                    foundFileCol = true;
                                }                            
                                if(foundFileCol && !stringCellValue.equalsIgnoreCase("BibNo_Result")){
                                    //System.out.println(dcIdentifier+":"+j);
                                    if(j == hasBibNo ){
                                         getBib.add(stringCellValue);                     
                                    }                  
                                }      
                        }
                }
                return getBib;               
        }
        public static ArrayList<String> matchBibNo(ArrayList<List<String>> cellVectorHolder,ArrayList<String> alBib){
                boolean foundFileCol =false;
                ArrayList<String> getBib = new ArrayList<String>();
                String stringCellValue = "";
                ArrayList<String> cellStoreVector = new ArrayList<String>();  
                Integer hasBibNo = -1 ;
                boolean isMatch = false;
                ArrayList<String> header  = new ArrayList<String>();
                for (int i = 0; i <  cellVectorHolder.size(); i++) {
                                cellStoreVector = (ArrayList<String>)  cellVectorHolder.get(i);
                                for (int j = 0; j < cellStoreVector.size(); j++) {
                                        stringCellValue = cellStoreVector.get(j);
                                            if(i==0){
                                                header.add(stringCellValue);

                                                if(stringCellValue.equalsIgnoreCase("BibNo")){
                                                hasBibNo = j ;
                                                foundFileCol = true;
                                            }                                             
                                        }
                                         
                                        if(foundFileCol && !stringCellValue.equalsIgnoreCase("BibNo")){
                                            if(j == hasBibNo ){      
                                                  for (int k = 0 ; k < alBib.size();k++){
                                                      if(stringCellValue.equalsIgnoreCase(alBib.get(k))){
                                                          isMatch = true;
                                                          break;
                                                      }
                                                  }                          
                                            }                  
                                        }                                       
                                }
                                
                                if(isMatch || i==0){
                                    String concat ="";
                                    for (int j = 0; j < cellStoreVector.size(); j++) {
                                        if(i==0){
                                            if(j==0){
                                                concat += header.get(j);
                                            }
                                            else {
                                                concat += ","+header.get(j);
                                            }
                                            
                                        }
                                        else{
                                            stringCellValue = cellStoreVector.get(j);
                                            stringCellValue.replace(",", "'");
                                            
                                                if(j==0){
                                                    concat += stringCellValue;
                                                }
                                                else{
                                                    concat += "," +stringCellValue;
                                                }                       
                                            
                                        }
                                            
                                    }
                                    getBib.add(concat);
                                    isMatch = false;
                                }
                                
                                
                                 

        
                        }
                return getBib;
        }
        
        public static ArrayList<String> getFileMiss(ArrayList<List<String>> dataHolder,String iFileName,String logFolder) {
                boolean foundFileCol =false;
                ArrayList<String> getLog = new ArrayList<String>();
                String sender = "";
                String stringCellValue = "";
                ArrayList<String> cellStoreVector = new ArrayList<String>();  
                Integer dcIdentifier = -1 ;
                String tempText = "";
                Integer countFirstCol = 0;
                Integer countCol = 0;
                for (int i = 0; i < dataHolder.size(); i++) {
                        cellStoreVector = (ArrayList<String>) dataHolder.get(i);
                        countCol = cellStoreVector.size();
                        
                        if (i == 0){
                            countFirstCol = cellStoreVector.size();

                        }                        
                        if (countCol != countFirstCol){
                            WriteText wt = new WriteText();
                            ArrayList<String> gl = new ArrayList<String>();
                            gl.add("แถวที่ :["+(i+1)+"]  มีค่าว่าง ไม่สามารถดำเนินการต่อได้ กรุณาแก้ไขเอกสาร Excel ให้ไม่มีค่าว่าง");
                            wt.writeLog(gl, logFolder);
                            System.exit(1);
                        }
                        for (int j = 0; j < countCol; j++) {
                                stringCellValue = cellStoreVector.get(j);
                                
                                if(stringCellValue.equalsIgnoreCase("dc.identifier")){
                                    dcIdentifier = j ;
                                    foundFileCol = true;
                                }
                             
                                if(foundFileCol && !stringCellValue.equalsIgnoreCase("dc.identifier")){
                                    //System.out.println(dcIdentifier+":"+j);
                                    if(j == dcIdentifier ){
                                       cfe = new ChkFileExist();
                                       sender = iFileName +"//"+stringCellValue+"" ;  
                                       //System.out.println(sender);          
                                       String writeLog  = cfe.getFileName(sender);
                                       tempText = writeLog;
                                    }
                                 
                                }      
                                //System.out.print(stringCellValue + "\t");
                        }
                        if(i != 0){
                         tempText= tempText+"#"+i;       
                                    getLog.add(tempText);
                        
                        tempText="";
                        }
                        //System.out.println();
                }
                return getLog;
                
        }
        public static ArrayList<String> getIdentifier(ArrayList<List<String>> dataHolder){  
            ArrayList<String> nVec = new ArrayList<String>();
            String stringCellValue = "";
            ArrayList<String> cellStoreVector = new ArrayList<String>();  
            Integer dcIdentifier = 0 ;
            String tempText= "";            
                for (int i = 0; i < dataHolder.size(); i++) {
                        cellStoreVector = (ArrayList<String>) dataHolder.get(i);
                        for (int j = 0; j < cellStoreVector.size(); j++) {
                                stringCellValue = cellStoreVector.get(j);                
                                if(stringCellValue.equalsIgnoreCase("dc.identifier")){
                                    dcIdentifier = j ;
                                    break;
                                }  
                        }
                        if (dcIdentifier > 0) {
                            stringCellValue = cellStoreVector.get(dcIdentifier);
                            tempText = stringCellValue;
                            if(!tempText.equals("") && 
                               !tempText.equals("-") &&
                               i > 0){
                                    nVec.add(tempText);
                            }
                            tempText = "" ;
                        }             
                }
                return nVec;         
        }
        
        
        public static void ReadForWriteExcel(String fileName,File folder) {
                ArrayList<List<String>> cellVectorHolder = new ArrayList<List<String>>();
                
                try {                        
                        FileInputStream myInput = new FileInputStream(fileName);
                        POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);
                        HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
                        HSSFSheet mySheet = myWorkBook.getSheetAt(0);
                        HSSFRow myRow;
                        HSSFCell myCell;   
                        Iterator rowIter = mySheet.rowIterator();                                             
                        Iterator cellIter;
                        ArrayList<String> cellStoreVector = new ArrayList<String>();
                        
                        
                        while (rowIter.hasNext()) {
                                myRow = (HSSFRow) rowIter.next();
                                cellIter = myRow.cellIterator();
                                cellStoreVector = new ArrayList<String>();
                                while (cellIter.hasNext() ) {
                                        myCell = (HSSFCell) cellIter.next();
                                        cellStoreVector.add(myCell.toString());
                                }
                                cellVectorHolder.add(cellStoreVector);
                        }                       
               
                        String stringCellValue = "";                 
                        Integer dcIdentifierID = -1 ;
                        Integer dcDescTableContentID = -1;
                        String tempText = "";
                        ArrayList<String> listFile = new ArrayList<String>();
                        ArrayList<String> imBookmark = new ArrayList<String>();
                        ChkFileExist cfe = new ChkFileExist();
                        listFile = cfe.listFilesForFolder(folder);
                        String idenCell = "";
                        String descTocCell= "";
                        int size = cellVectorHolder.size();
                        ArrayList<String> cellHeaderVector = new ArrayList<String>();
                        cellHeaderVector = (ArrayList<String>)  cellVectorHolder.get(0);
                        int hSize = cellHeaderVector.size() ;
                        for (int j = 0; j < hSize; j++) {
                                        stringCellValue = cellHeaderVector.get(j);
                                        if(stringCellValue.equalsIgnoreCase("dc.identifier")){
                                            dcIdentifierID = j ;
                                        }
                                        if(stringCellValue.equalsIgnoreCase("dc.description.tableofcontent")){
                                            dcDescTableContentID = j ;
                                        }
                        }
                        
                        if(dcDescTableContentID < 0){
                                    dcDescTableContentID = hSize;
                                    hSize++;
                                    myRow = mySheet.getRow(0);
                                    //Row tempRow = mySheet.getRow(0);
                                    myRow.createCell(dcDescTableContentID);
                                    myCell = myRow.getCell(dcDescTableContentID);
                                    myCell.setCellValue("dc.description.tableofcontent");
                                    //System.out.println("descID :"+dcDescTableContentID);
                                    for(int i = 1 ; i < size ; i++){
                                        myRow = mySheet.getRow(i);
                                        myRow.createCell(dcDescTableContentID);
                                        myCell = myRow.getCell(dcDescTableContentID);
                                        myCell.setCellValue("-");
                                    }

                                    //myCell = myRow.getCell(dcDescTableContentID);
                                    //System.out.println("val  :"+myCell.getStringCellValue().toString());
                                    //myCell.setCellValue("dc.description.tableofcontent");
                                    System.out.println("descID :"+dcDescTableContentID);
                        }
                        
                        for (int i = 1; i <  size; i++) {
                                cellStoreVector = (ArrayList<String>)  cellVectorHolder.get(i);
                                if (dcIdentifierID > 0 && dcDescTableContentID > 0 ){
                                        idenCell = cellStoreVector.get(dcIdentifierID);
                                        if( !idenCell.equals("") && 
                                            !idenCell.equals("-") &&
                                            i > 0){
                                            idenCell += ".txt";
                                            int fSize =  listFile.size() ;
                                            for (int k = 0 ; k < fSize ; k++){
                                                    
                                                    if (idenCell.equalsIgnoreCase(listFile.get(k))){                                
                                                        descTocCell = readText(idenCell,folder);   
                                                        myRow = mySheet.getRow(i);
                                                        myCell = myRow.getCell(dcDescTableContentID);
                                                        //System.out.println(descTocCell);
                                                        myCell.setCellValue(descTocCell);
                                                        break;
                                                    }
                                                    
                                            }                           
                                        }     

                                        //System.out.print(stringCellValue + "\t");
                                }
                                //System.out.println();
                                myInput.close();   
                                FileOutputStream fileOut = new FileOutputStream(fileName);
                                myWorkBook.write(fileOut);
                                fileOut.close();
                        }

                         } catch (Exception e) {
                        }
                       
              
                
                
                
        }
        private static String readText(String iStr,File iFolder){
        String str = "";
        String fullPath = "";            
               fullPath =  iFolder.toString() + "\\" + iStr ;
               str = parseBookmark(fullPath);
               
        return str;
        }
        private static String parseBookmark (String iFileName){
           String concatStr = "";
           String str = "";
           String[] splitPage;
           String title ="";
           String page = "";
      

           String childTag = "<child>";
           int childTagLen = childTag.length();
           int lvPointer = 0;
           int tempTagCount = 0;
           ArrayList<String> aTitle = new ArrayList<String>();
           ArrayList<String> aPage = new ArrayList<String>();
           ArrayList<Integer> aChildLv = new ArrayList<Integer>();
           String[] aBookmark;
           //ArrayList<String[]> al =new  ArrayList<String[]>();
           int tempVal = 0;
           int maxLevel = 0;
                   try {
                        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(iFileName), "TIS620"));
                            while ((str = in.readLine()) != null) {
                                 splitPage = str.split("<page>");
                                 aTitle.add(splitPage[0]);
                                 aPage.add(getPageStringFromTagPage(splitPage[1]));
                                 aChildLv.add(countTag(splitPage[0],childTag));   
                            }
                            int size = aTitle.size();
                            aBookmark = new String[size];
                            
                            for (int i = 0 ; i < size ; i++){ // find maxLevel of this Bookmark && init aBookmark
                                tempVal = aChildLv.get(i);
                                if (aChildLv.get(i) > maxLevel){
                                    maxLevel = tempVal;
                                }
                                aBookmark[i] = "";

                            }
                            //System.out.println("max level :"+maxLevel);
                            int iiChild;
                            int cursorPos = 0;
                            String pageStart,pageEnd;
                            for (int i = 0 ; i <= maxLevel ; i++){
                               //System.out.println("bookmark Level :"+i + " Max Level : "+maxLevel);
                                pageStart = "";
                                pageEnd = "";
                                for (int ii = 0 ; ii < size ; ii++){
                                    iiChild = aChildLv.get(ii);
                                    if(iiChild == i  ){
                                        if(!pageStart.equalsIgnoreCase("")){
                                            if(ii+1 != size){
                                                if(aChildLv.get(cursorPos+1) < i){
                                                     pageEnd = aPage.get(cursorPos+1);
                                                     aBookmark[cursorPos] = createFullBookmarkString(aTitle.get(cursorPos),childTag,pageStart,pageEnd);
                                                     pageStart = pageEnd;
                                                }else{
                                                    pageEnd = aPage.get(ii);
                                                    aBookmark[cursorPos] = createFullBookmarkString(aTitle.get(cursorPos),childTag,pageStart,pageEnd);
                                                    pageStart = pageEnd;
                                                }
                                            }                                           
                                        }else{
                                            pageStart = aPage.get(ii);
                                        }
                                        cursorPos = ii;
                                    }
                                    if(ii+1 == size){
                                        if (cursorPos+1 != size){
                                            pageEnd = aPage.get(cursorPos+1);                                                                 
                                        }else{
                                            pageEnd = ""; 
                                        }
                                        aBookmark[cursorPos] = createFullBookmarkString(aTitle.get(cursorPos),childTag,pageStart,pageEnd);
                                    }
                                }
                            }
                            for(int i = 0 ; i < size ; i++){
                                concatStr += aBookmark[i].trim();
                            }

                        in.close();
                    } 
                    catch (Exception e) {

                    }                        

            return concatStr;
        }
        private static String createFullBookmarkString (String title , String childTag , String pageStart , String pageEnd){
            if(!pageEnd.equals("")){
                pageEnd = "," + pageEnd;
            }
            String temp = title.replace(childTag, ">").trim() + "|#page=" + pageStart +pageEnd + "|||";
            return temp.replaceAll("[^\\x0A\\x0D\\x20-\\x7E]", "");
        }
        private static String getPageStringFromTagPage(String tagPage){
            int commaPos = tagPage.indexOf(",");
            tagPage = tagPage.substring(0,commaPos);
            return tagPage;
        }
        private static int countTag(String title,String tag){
            int tagCount = 0;
            /*for (int i = 0 ; i < title.length() ; i++){
                 if(title.charAt(i) == '<' && title.substring(i, i+tag.length()).equals(tag)){
                     tagCount++;
                     i += tag.length();
                 }
            }*/
            while(title.contains(tag)){
                title = title.replaceFirst(tag, "");
                tagCount++;
            }
            return tagCount;
        }
        public static void ReadForWriteXML (ArrayList<List<String>> dataHolder , String fileFolder){
            System.out.println("start writing:");
            ArrayList<String> alStr = new ArrayList<String>();
            String stringCellValue = "";
            ArrayList<String> cellStoreVector = new ArrayList<String>();
            ArrayList<String> headList = (ArrayList<String>) dataHolder.get(0);
            ArrayList<String> headTag = new ArrayList<String>();       
            String header = "";
            String[] spHeader;
            String qualifier = "";
            String newFolder ="";
            Integer dcDateIssued,dcDateSubmit,dcParSitDate ;
            dcDateIssued = -1;dcDateSubmit=-1;dcParSitDate=-1;
            String[] spCon ;
            Integer i,s,j;
            WriteText wt = new WriteText();
            boolean isDate = false;
                for (i = 0; i < dataHolder.size(); i++) {
                        cellStoreVector = (ArrayList<String>) dataHolder.get(i);
                        alStr.clear();
                        alStr.add("<?xml version=\"1.0\" encoding=\"tis-620\"?>");                        
                        alStr.add("<dublin_core>");
                        
                        for (j = 0; j < cellStoreVector.size(); j++) {                               
                                stringCellValue = cellStoreVector.get(j);
                                    if (!stringCellValue.trim().equals("-")){
                                        if (i == 0){ //assign headerList
                                                if(stringCellValue.equalsIgnoreCase("dc.date.issued")){
                                                dcDateIssued = j ;
                                                }
                                                else if(stringCellValue.equalsIgnoreCase("dc.date.submit")){
                                                    dcDateSubmit = j ;
                                                }
                                                else if(stringCellValue.equalsIgnoreCase("dc.parliament.sittingDate")){
                                                    dcParSitDate = j ;
                                                }

                                            header = headList.get(j);
                                            header = header.replace("dc.", "");
                                            header = header.replace(".",":");
                                            //header = header.toLowerCase();
                                            if(header.indexOf("sittingDate") < 0 && header.indexOf("sittingNo") < 0){
                                                header = header.toLowerCase();
                                            }
                                            spHeader = header.split(":");
                                            if (spHeader.length == 1) {
                                                spHeader[0] = header;
                                                qualifier = "none";
                                            }
                                            else if (spHeader.length == 2){
                                                qualifier = spHeader [1];                                   
                                            }        
                                            else if (spHeader.length == 3){
                                                qualifier = spHeader [1] + "-" + spHeader[2];                                   
                                            }
                                            headTag.add("<dcvalue element=\""+spHeader[0]+"\" qualifier=\""+qualifier+"\">" );                                   
                                        }
                                        else{
                                            
                                            stringCellValue = parseStr(stringCellValue);
                                            isDate = (j == dcDateIssued || j == dcDateSubmit || j== dcParSitDate);
                                            if(isDate){
                                               stringCellValue  = parseDate(stringCellValue);
                                            }
                                            
                                            spCon = stringCellValue.split("<sp>");                                        
                                                for (s = 0 ;s < spCon.length ;s++){
                                                    if (!spCon[s].equals("")){
                                                        alStr.add(headTag.get(j) + spCon[s] +"</dcvalue>");    
                                                        
                                                    }
                                                }
                                                newFolder = fileFolder + "//"+i;                                            
                                        }
                                            
                                    }
                        }
                        if (i > 0){
                        alStr.add("</dublin_core>");
                        File theDir = new File(newFolder);
                        if (!theDir.exists())
                          {
                            boolean result = theDir.mkdir();
                            if(result){
                               System.out.println("create folder:"+result);
                             }
                          }
                        wt.write(alStr, newFolder+"\\dublin_core.xml");   
                        ChkFileExist cfe = new ChkFileExist();
                        File dirFolder = new File(newFolder);                     
                        ArrayList<String> str = cfe.listFilesForFolder(dirFolder);                       
                        ArrayList<String> lst = new ArrayList<String>();
                        String readStr ="";          
                            for (int y = 0 ; y < str.size() ; y ++){
                                readStr = str.get(y);
                                if(!readStr.equalsIgnoreCase("dublin_core.xml") && !readStr.equalsIgnoreCase("index")&& !readStr.equalsIgnoreCase("contents")){
                                    lst.add(readStr);
                                    wt.write(lst, newFolder+"\\contents","UTF-8");
                                }
                            }
                        }

                }
        }
        private static String parseStr(String stringCellValue) {
                                stringCellValue = stringCellValue.trim();
                                stringCellValue = stringCellValue.replace("&", "&amp;");
                                stringCellValue = stringCellValue.replace("<", "&lt;");
                                stringCellValue = stringCellValue.replace(">", "&gt;");
                                stringCellValue = stringCellValue.replace("'", "&apos;");
                                stringCellValue = stringCellValue.replace("`", "&apos;");
                                stringCellValue = stringCellValue.replace("’", "&apos;");
                                stringCellValue = stringCellValue.replace("\"", "&quot;");
                                stringCellValue = stringCellValue.replace("“", "&quot;");
                                stringCellValue = stringCellValue.replace("”", "&quot;");
                                //stringCellValue = stringCellValue.replace("-", "--"); 
                                stringCellValue = stringCellValue.replace("|||", "<sp>");
            return stringCellValue;
        }
        private static String parseDate(String stringCellValue) {
            String yyyy,mm,dd;
            if (stringCellValue.indexOf("/") != -1){
            String k[] = stringCellValue.split("/");
                if (k[0].length() <= 2){  //if DD/MM/YYYY
                        if(k[0].length()== 1)dd = "0"+k[0];
                        else dd = k[0];
                        if (k[1].length()==1)mm = "0"+k[1];
                        else mm = k[1];
                        yyyy = k[2];
                        stringCellValue = yyyy+ "-"+mm+"-"+dd;
                }
                else if (k[0].length() == 4){ // if YYYY/MM/DD
                        if(k[2].length()== 1)dd = "0"+k[2];
                        else dd = k[2];
                        if (k[1].length()==1)mm = "0"+k[1];
                        else mm = k[1];
                        yyyy = k[0];
                        stringCellValue = yyyy+ "-"+mm+"-"+dd;
                }            
            }
            else if (stringCellValue.indexOf("-") != -1){
            String k[] = stringCellValue.split("-");
                if (k[0].length() <= 2){  //if DD-MM-YYYY
                        if(k[0].length()== 1)dd = "0"+k[0];
                        else dd = k[0];
                        if (k[1].length()==1)mm = "0"+k[1];
                        else mm = k[1];
                        yyyy = k[2];
                        stringCellValue = yyyy+ "-"+mm+"-"+dd;
                }
                else if (k[0].length() == 4){ // if YYYY-MM-DD     
                    // do nothing
                }            
            }
            else if (stringCellValue.length() == 8){//YYYYMMDD
                yyyy = stringCellValue.substring(0, 4);
                mm = stringCellValue.substring(0, 6);
                stringCellValue = stringCellValue.replace(mm, mm+"-");
                stringCellValue = stringCellValue.replace(yyyy, yyyy+"-");
            }
            //else if ()
            return stringCellValue;
        }
        
        
}
