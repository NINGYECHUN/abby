package com.esm.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;

/**
 * Excel导出公共类
 * @author 宁业春
 */
public class PoiUtil {
	
	private static Logger log = (Logger) LogManager.getLogger(PoiUtil.class.getName());

    /*public static void exportExcel(Collection<JSONObject> dataset,List<String> columns, OutputStream out, String headerContent)  throws Exception {
        exportExcel("sheet1", null, null, dataset, out, "yyyy-MM-dd", null, headerContent);
    }*/
	
	private static DecimalFormat  df = new DecimalFormat("0.##"); 

    public static void exportExcel(String[] headers,HashMap<String, Object> headerValue, Collection<JSONObject> dataset,List<String> columns,
            OutputStream out, Map<String, Object> styleMap, String headerContent)  throws Exception {
        exportExcel("sheet1", headers, headerValue,dataset,  out, "yyyy-MM-dd", styleMap, headerContent);
    }
    
    public static void exportExcel(String[] headers,HashMap<String, Object> headerValue, Collection<JSONObject> dataset,List<String> columns,
            OutputStream out, String headerContent)  throws Exception {
        exportExcel("sheet1", headers,headerValue, dataset,  out, "yyyy-MM-dd", null, headerContent);
    }

    public static void exportExcel(String[] headers,HashMap<String, Object> headerValue, Collection<JSONObject> dataset, List<String> columns,
            OutputStream out, String pattern, Map<String, Object> styleMap, String headerContent)  throws Exception{
        exportExcel("sheet1", headers, headerValue,dataset, out, pattern, styleMap, headerContent);
    }
    
    public static void exportExcel(String[] headers,HashMap<String, Object> headerValue, Collection<JSONObject> dataset, List<String> columns,
            OutputStream out, String pattern, String headerContent)  throws Exception{
        exportExcel("sheet1", headers, headerValue,dataset, out, pattern, null,headerContent);
    }
    
    public static void exportExcel(LinkedHashMap<String, Object> headerValue, Collection<JSONObject> dataset,
            OutputStream out, String headerContent)  throws Exception {
    	String[] headers = {};
    	headers = headerValue.keySet().toArray(headers);
        exportExcel("sheet1", headers,headerValue, dataset, out, "yyyy-MM-dd", null, headerContent);
    }

    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * 
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public static void exportExcel(String title, String[] headers, HashMap<String, Object> headerValue,
            Collection<JSONObject> dataset, OutputStream out, String pattern, Map<String, Object> styleMap, String headerContent) throws Exception {
    	
    	 HSSFWorkbook workbook = null;
    	try {
        // 声明一个工作薄
        workbook = new HSSFWorkbook();
        // 生成一个表格
        workbook.createSheet(title);
       writeToExcel(workbook,0, null,headers, headerValue, dataset, pattern, styleMap, headerContent,null,null);
        
        workbook.write(out);
        
    	 } catch (SecurityException e) {
             // TODO Auto-generated catch block
    		 log.error(e.getMessage(),e);
         } catch (IllegalArgumentException e) {
             // TODO Auto-generated catch block
        	 log.error(e.getMessage(),e);
         } finally {
             // 清理资源
        	 try{
        		 if(workbook != null){
        			 //workbook.close();
        		 }
        	 }catch(Exception e){
        		 log.error(e.getMessage(),e);
        	 }
         }
    }
    
    /**
     * 往workbook里面写入excel信息
     * @param workbook 要写入信息的workbook
     * @param beginRow 起始行
     * @param beginCol 起始列 ，从1开始，如果不指定，默认是1
     * @param headerValue 存放标题code和name的map
     * @param dataset JSONObject格式的数据集合
     * @param styleMap 一些样式 
     *                            包括colHeight（列高）,colWidth（列宽）,fontSize（字体大小）,picHeight（图片高度）,
     *                            picWidth(图片宽度),columnWidthList(ColumnWidth数据类型的list)
     * @param headerContent 页眉内容
     * @param headerStyle 标题行样式 
     * @param sheet
     * @throws IOException IO异常
     */
    public static void writeToExcel(HSSFWorkbook workbook,Integer beginRow,Integer beginCol,LinkedHashMap<String, Object> headerValue, 
    		Collection<JSONObject> dataset,String headerContent,HSSFCellStyle headerStyle,HSSFSheet sheet)  throws Exception {
    	String[] headers = {};
    	headers = headerValue.keySet().toArray(headers);
    	writeToExcel(workbook,beginRow,beginCol, headers,headerValue, dataset, "yyyy-MM-dd", null, headerContent,headerStyle,sheet);
    }
    
    /**
     * 往workbook里面写入excel信息
     * @param workbook 要写入信息的workbook
     * @param beginRow 起始行
     * @param beginCol 起始列 ，从1开始，如果不指定，默认是1
     * @param headerValue 存放标题code和name的map
     * @param dataset JSONObject格式的数据集合
     * @param styleMap 一些样式 
     *                            包括colHeight（列高）,colWidth（列宽）,fontSize（字体大小）,picHeight（图片高度）,
     *                            picWidth(图片宽度),columnWidthList(ColumnWidth数据类型的list)
     * @param headerContent 页眉内容
     * @throws IOException IO异常
     */
    public static void writeToExcel(HSSFWorkbook workbook,Integer beginRow,Integer beginCol,LinkedHashMap<String, Object> headerValue, 
    		Collection<JSONObject> dataset,String headerContent,HSSFSheet sheet)  throws Exception {
    	String[] headers = {};
    	headers = headerValue.keySet().toArray(headers);
    	writeToExcel(workbook,beginRow,beginCol, headers,headerValue, dataset, "yyyy-MM-dd", null, headerContent,null,sheet);
    }
    
    /**
     * 往workbook里面写入excel信息
     * @param workbook 要写入信息的workbook
     * @param beginRow 起始行
     * @param beginCol 起始列 ，从1开始，如果不指定，默认是1
     * @param headers 标题code属性
     * @param headerValue 存放标题code和name的map
     * @param dataset JSONObject格式的数据集合
     * @param pattern 要输出的时间格式
     * @param styleMap 一些样式 
     *                            包括colHeight（列高）,colWidth（列宽）,fontSize（字体大小）,picHeight（图片高度）,
     *                            picWidth(图片宽度),columnWidthList(ColumnWidth数据类型的list)
     * @param headerContent 页眉内容
     * @param headerStyle 标题行的样式
     * @param sheet
     * @throws IOException IO异常
     */
    public static void writeToExcel(HSSFWorkbook workbook,Integer beginRow,Integer beginCol,
    		String[] headers, HashMap<String, Object> headerValue,Collection<JSONObject> dataset,
    		String pattern, Map<String, Object> styleMap, String headerContent,HSSFCellStyle headerStyle,
    		HSSFSheet sheet) throws IOException{
    	//自定义字体大小, 行高, 行宽
    	Short colHeight = null;
    	Short colWidth = null;
    	Short fontSize = null;
    	Float picHeight = null;
    	Float picWidth = null;
    	List<ColumnWidth> columnWidthList = null;
    	try {
    	if(styleMap != null){
    		colHeight = (Short) styleMap.get("colHeight");
    		colWidth = (Short)styleMap.get("colWidth");
    		fontSize = (Short) styleMap.get("fontSize");
    		picHeight = (Float)styleMap.get("picHeight");
    		picWidth = (Float)styleMap.get("picWidth");
    		columnWidthList = (List<ColumnWidth>) styleMap.get("columnWidthList");
    	}
        // 生成一个表格
    	if(sheet == null){
    		sheet = workbook.getSheetAt(0);
    	}
        //设置打印配置
        HSSFPrintSetup ps = sheet.getPrintSetup();
        ps.setLandscape(false); //打印方向，true:横向，false:纵向
        ps.setPaperSize(HSSFPrintSetup.A4_PAPERSIZE); //纸张
        sheet.setMargin(HSSFSheet.BottomMargin, (double)0.2); //页边距（下）
        sheet.setMargin(HSSFSheet.LeftMargin, (double)0.2); //页边距（左）
        sheet.setMargin(HSSFSheet.RightMargin, (double)0.2); //页边距（右）
        sheet.setMargin(HSSFSheet.TopMargin, (double)1); //页边距（上）
        sheet.setHorizontallyCenter(true); //设置打印页面为水平居中
       // sheet.setPrintGridlines(true);

        
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
      //列宽
        if(colWidth != null){
        	sheet.setDefaultColumnWidth(colWidth);
        }
        //根据设置具体设置每列的宽度
        if(columnWidthList != null && columnWidthList.size() > 0){
        	for(ColumnWidth cw : columnWidthList){
        		sheet.setColumnWidth(cw.getColumnNum(), cw.getWidth());
        	}
        }
      //行高
        if(colHeight != null){
        	sheet.setDefaultRowHeight(colHeight.shortValue());
        }
        
        // 生成一个样式
        if(headerStyle==null){
        	headerStyle = getHeaderStyle(workbook);
        }
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        //style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        style2.setWrapText(true);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        if(fontSize != null){
        	 font2.setFontHeightInPoints(fontSize.shortValue());
        }else{
        	 font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        }
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        
        // 产生页眉
        if(!StringUtils.isBlank(headerContent))
        {
	        Header header = sheet.getHeader();
	        //自定义页眉,并设置页眉 左中右显示信息
	        header.setCenter(HSSFHeader.font("Stencil-Normal", "Italic") +
                    HSSFHeader.fontSize((short) 16) + headerContent);
        }

        // 产生表格标题行
        if(beginRow == null){
        	beginRow = 0;
        }else if(beginRow > 1){
    		beginRow = beginRow - 1;
    	}
        
        if(beginCol == null){
        	beginCol = new Integer(1);
        }
        beginCol = beginCol - 1;
        HSSFRow row = sheet.getRow(beginRow);
        if(row == null){
        	row = sheet.createRow(beginRow);
        }
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i+beginCol);
            if(headerValue == null){
            	headerValue = new HashMap<String, Object>();
            }
            Object value = headerValue.get(headers[i]);
            if(value != null){
            if (value instanceof File) {
                // 有图片时，设置行高为60px;
            	if(colHeight != null){
            		row.setHeightInPoints(picHeight);
            	}else{
            		row.setHeightInPoints(80);
            	}
                // 设置图片所在列宽度为80px,注意这里单位的一个换算
            	if(picWidth != null){
            		sheet.setColumnWidth(i, (short)(35.7*picWidth));
            	}else{
            		sheet.setColumnWidth(i, (short) (35.7 * 120));
                }
                ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                if(!((File)value).exists()){
                	continue;
                }
                BufferedImage bufferImg = ImageIO.read((File)value);
                Float pWidth = new Float(bufferImg.getWidth());
                Float pHeight = new Float(bufferImg.getHeight());
                Float dx1;
                Float dy1;
                Float dx2;
                Float dy2;
                if(pWidth > pHeight){
                	dx1 = 10f ;
                	dx2 = 1013f;
                	Float newPHeight=  ((new Float(pHeight)/new Float(pWidth))*1*255);
                	dy1 =  ((255 - newPHeight)/2);
                	dy2 =  (dy1 + newPHeight);
                }else if(pHeight > pWidth){
                	dy1 = 1f;
                	dy2 = 254f;
                	float newPWidth = (pWidth/pHeight)*1*1023;
                	dx1 = (1023 - newPWidth)/2;
                	dx2 = dx1 + newPWidth;
                }else{
                	dy1 = 1f ;
                	dx1 = 10f;
                	dy2 = 254f;
                	dx2 = 1013f;
                }
                ImageIO.write(bufferImg,"jpg",byteArrayOut); 
                // sheet.autoSizeColumn(i);
               // byte[] bsValue = (byte[]) value;
                /*HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                        1023, 255, (short) i, index, (short) i, index);*/
                HSSFClientAnchor anchor = new HSSFClientAnchor(dx1.intValue(), dy1.intValue(),
                        dx2.intValue(), dy2.intValue(), (short) i, 0, (short) i, 0);
                anchor.setAnchorType(2);
                patriarch.createPicture(anchor, workbook.addPicture(
                		byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
            }else{
            	cell.setCellStyle(headerStyle);
            	cell.setCellValue(new HSSFRichTextString(value.toString()));
            }
            }else{
            	cell.setCellStyle(headerStyle);
            	HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            	cell.setCellValue(text);
            }
        }

        // 遍历集合数据，产生数据行
        Iterator<JSONObject> it = dataset.iterator();
        int index = beginRow;
        while (it.hasNext()) {
            index++;
            row = sheet.getRow(index);
            if(row == null){
            	row = sheet.createRow(index);
            }
            JSONObject t = (JSONObject) it.next();
            // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
           
            for (short i = 0; i < headers.length; i++) {
            	//String columnN
                HSSFCell cell = row.createCell(i+beginCol);
                cell.setCellStyle(style2);
                    
                    Object value = t.get(headers[i]);
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    
                    if(value == null){
                    	continue;
                    }else if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "YES";
                        if (!bValue) {
                            textValue = "NO";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG)).resize(1.0);
                    } else if (value instanceof File) {
                        // 有图片时，设置行高为60px;
                    	if(colHeight != null){
                    		row.setHeightInPoints(picHeight);
                    	}else{
                    		row.setHeightInPoints(80);
                    	}
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                    	if(picWidth != null){
                    		sheet.setColumnWidth(i, (short)(35.7*picWidth));
                    	}else{
                    		sheet.setColumnWidth(i, (short) (35.7 * 120));
                        }
                        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
                        if(!((File)value).exists()){
                        	continue;
                        }
                        BufferedImage bufferImg = ImageIO.read((File)value);
                        Float pWidth = new Float(bufferImg.getWidth());
                        Float pHeight = new Float(bufferImg.getHeight());
                        Float dx1;
                        Float dy1;
                        Float dx2;
                        Float dy2;
                        if(pWidth > pHeight){
                        	dx1 = 10f ;
                        	dx2 = 1013f;
                        	Float newPHeight=  ((new Float(pHeight)/new Float(pWidth))*1*255);
                        	dy1 =  ((255 - newPHeight)/2);
                        	dy2 =  (dy1 + newPHeight);
                        }else if(pHeight > pWidth){
                        	dy1 = 1f;
                        	dy2 = 254f;
                        	float newPWidth = (pWidth/pHeight)*1*1023;
                        	dx1 = (1023 - newPWidth)/2;
                        	dx2 = dx1 + newPWidth;
                        }else{
                        	dy1 = 1f ;
                        	dx1 = 10f;
                        	dy2 = 254f;
                        	dx2 = 1013f;
                        }
                        ImageIO.write(bufferImg,"jpg",byteArrayOut); 
                        // sheet.autoSizeColumn(i);
                       // byte[] bsValue = (byte[]) value;
                        /*HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) i, index, (short) i, index);*/
                        HSSFClientAnchor anchor = new HSSFClientAnchor(dx1.intValue(), dy1.intValue(),
                                dx2.intValue(), dy2.intValue(), (short) i, index, (short) i, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                        		byteArrayOut.toByteArray(), HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else {
                        // 其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            // 是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(
                                    textValue);
                            /*HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLUE.index);
                            if(fontSize != null){
                            	font3.setFontHeightInPoints(fontSize);
                            }
                            richString.applyFont(font3);*/
                            cell.setCellValue(richString);
                        }
                    }
            }

        }
        
        sheet.setRepeatingColumns(new CellRangeAddress(-1, -1, 0, 0));
        
    	 } catch (SecurityException e) {
             // TODO Auto-generated catch block
    		 log.error(e.getMessage(),e);
         } catch (IllegalArgumentException e) {
             // TODO Auto-generated catch block
        	 log.error(e.getMessage(),e);
         } finally {
        	 //资源在外面释放
         }
    }
   
    /**
     * 往workbook里面写入excel信息
     * @param workbook 要写入信息的workbook
     * @param beginRow 起始行
     * @param headers 标题code属性
     * @param headerValue 存放标题code和name的map
     * @param dataset JSONObject格式的数据集合
     * @param pattern 要输出的时间格式
     * @param styleMap 一些样式 
     *                            包括colHeight（列高）,colWidth（列宽）,fontSize（字体大小）,picHeight（图片高度）,
     *                            picWidth(图片宽度),columnWidthList(ColumnWidth数据类型的list)
     * @param headerContent 页眉内容
     * @param sheet 当前操作的sheet,如果为空，默认为第一个sheet
     * @throws IOException IO异常
     */
    public static void writeExcelMainTtitle(HSSFWorkbook workbook,Integer beginRow,
    		Integer fontSize, Integer colSize, String title , HSSFSheet sheet) throws IOException{
    	
    	try {
    	
	        // 生成一个表格
    		if(sheet == null){
    			sheet = workbook.getSheetAt(0);
    		}
	        // 生成一个样式
	        HSSFCellStyle style = workbook.createCellStyle();
	        // 设置这些样式
	        style.setFillForegroundColor(HSSFColor.WHITE.index);
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	        style.setWrapText(true);
	        // 生成一个字体
	        HSSFFont font = workbook.createFont();
	        //font.setColor(HSSFColor.BLUE_GREY.index);
	        font.setFontHeightInPoints((short) 12);
	        if(fontSize != null){
	        	font.setFontHeightInPoints(fontSize.shortValue());
	        }
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        // 把字体应用到当前的样式
	        style.setFont(font);
	        
	        // 产生表格标题行
	        if(beginRow == null){
	        	beginRow = 0;
	        }else if(beginRow > 1){
        		beginRow = beginRow - 1;
        	}
	        HSSFRow row = sheet.createRow(beginRow);
	        row.setHeightInPoints(font.getFontHeightInPoints()+10);
	        if(colSize == null){
	        	colSize = 14;
	        }
	        for (short i = 0; i < colSize; i++) {
	            HSSFCell cell = row.createCell(i);
	            cell.setCellStyle(style);
	        }
	        HSSFCell firstCell = row.getCell(0); 
	        firstCell.setCellStyle(style);
	    	HSSFRichTextString text = new HSSFRichTextString(title);
	    	firstCell.setCellValue(text);
	    	sheet.addMergedRegion(new CellRangeAddress(beginRow,beginRow,0,colSize));
        
    	 } catch (SecurityException e) {
             // TODO Auto-generated catch block
    		 log.error(e.getMessage(),e);
         } catch (IllegalArgumentException e) {
             // TODO Auto-generated catch block
        	 log.error(e.getMessage(),e);
         } finally {
        	 //资源在外面释放
         }
    }
    
    /**
     * 合并行和列(已)
     * @param book
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param sheet
     */
    public static void merge(HSSFWorkbook book ,int firstRow, int lastRow, int firstCol, int lastCol,HSSFCellStyle style, String content,HSSFSheet sheet){
    	firstRow = firstRow-1;
    	lastRow =lastRow-1;
    	firstCol = firstCol -1;
    	lastCol =lastCol - 1;
    	if(sheet == null){
    		sheet = book.getSheetAt(0);
    	}
    	HSSFRow row = sheet.getRow(firstRow);
    	if(row == null){
    		row = sheet.createRow(2);
    	}
    	HSSFCell  cell = row.getCell(firstCol);
    	if(cell == null){
    		cell = row.createCell(firstCol);
    	}
    	if(style != null){
    		for(int i=firstRow;i<=lastRow;i++){
    			HSSFRow r = sheet.getRow(i);
    			if(r == null){
    				r=sheet.createRow(i);
    			}
    			for(int j = firstCol; j <= lastCol; j++){
    				HSSFCell c = r.getCell(j);
    				if(c==null){
    					c=row.createCell(j);
    				}
    				c.setCellStyle(style);
    			}
    		}
    		cell.setCellStyle(style);
    	}
    	if(content != null){
    		cell.setCellValue(content);
    	}
    	sheet.addMergedRegion(new CellRangeAddress(firstRow,lastRow,firstCol,lastCol));
    }
    
    /**
     * 合并行和列(样式和内容以第一个单元格为准)
     * @param book
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @param sheet
     */
    public static void merge(HSSFWorkbook book ,int firstRow, int lastRow, int firstCol, int lastCol,HSSFSheet sheet){
    	//System.out.println("合并Excel数据：firstRow:"+firstRow+",lastRow:"+lastRow+",firstCol:"+firstCol+",lastCol:"+lastCol);
    	merge(book, firstRow, lastRow, firstCol, lastCol, null, null,sheet);
    }
    
    public static HSSFCellStyle getHeaderStyle2(HSSFWorkbook workbook){
    	 HSSFCellStyle style = workbook.createCellStyle();
         // 设置这些样式
         style.setFillForegroundColor(HSSFColor.LIGHT_ORANGE.index);
         style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         style.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
         style.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
         style.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
         style.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
         style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
         style.setWrapText(true);
         // 生成一个字体
         HSSFFont font = workbook.createFont();
         font.setColor(HSSFColor.BLACK.index);
         font.setFontHeightInPoints((short) 12);
         font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
         // 把字体应用到当前的样式
         style.setFont(font);
         return style;
    }
    
    public static HSSFCellStyle getHeaderStyle(HSSFWorkbook workbook){
   	 HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setWrapText(true);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        return style;
   }
    
    /**
     * 写excel行内容，不带header
     * @param workbook 
     * @param beginRow 开始行.
     * @param beginCol 开始列 从1开始
     * @param columns 需要写入的列的code数组
     * @param dataset 需要写入的列的数据集合，columns来识别这些数据
     * @param pattern 日期格式
     * @param sheet 
     * @throws IOException
     */
    public static void writeRowsWithoutHeader(HSSFWorkbook workbook,Integer beginRow, Integer beginCol,
    		String[] columns,Collection<JSONObject> dataset,String pattern,HSSFSheet sheet) throws IOException{
    	try {
    	
	        // 生成一个表格
    		if(sheet == null){
    			sheet = workbook.getSheetAt(0);
    		}
	        // 生成一个样式
	        HSSFCellStyle style = workbook.createCellStyle();
	        // 设置这些样式
	        style.setFillForegroundColor(HSSFColor.WHITE.index);
	        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
	        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
	        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
	        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
	        style.setAlignment(HSSFCellStyle.BORDER_THIN);
	        style.setWrapText(true);
	        // 生成一个字体
	        HSSFFont font = workbook.createFont();
	        //font.setColor(HSSFColor.BLUE_GREY.index);
	        font.setFontHeightInPoints((short) 12);
	        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
	        // 把字体应用到当前的样式
	        style.setFont(font);
	        
	        // 产生表格标题行
	        if(beginRow == null){
	        	beginRow = 0;
	        }else{
	        	if(beginRow > 2){
	        		beginRow = beginRow - 2;
	        	}
	        }
	        if(beginCol == null){
	        	beginCol = 0;
	        }else{
	        	beginCol = beginCol - 1;
	        }
	     // 遍历集合数据，产生数据行
	        Iterator<JSONObject> it = dataset.iterator();
	        int index = beginRow;
	        HSSFRow row = null;
	        while (it.hasNext()) {
	            index++;
	            row = sheet.createRow(index);
	            JSONObject t = (JSONObject) it.next();
	           
	            for (short i = 0; i < columns.length; i++) {
	                HSSFCell cell = row.createCell(beginCol);
	                cell.setCellStyle(style);
	                    
	                    Object value = t.get(columns[i]);
	                    // 判断值的类型后进行强制类型转换
	                    String textValue = null;
	                    
	                    if(value == null){
	                    	continue;
	                    }else if (value instanceof Boolean) {
	                        boolean bValue = (Boolean) value;
	                        textValue = "YES";
	                        if (!bValue) {
	                            textValue = "NO";
	                        }
	                    } else if (value instanceof Date) {
	                        Date date = (Date) value;
	                        if(pattern == null){
	                        	pattern = "yyyy-MM-dd";
	                        }
	                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	                        textValue = sdf.format(date);
	                    } else if (value instanceof byte[]) {
	                    	
	                    } else if (value instanceof File) {
	                    	
	                    } else {
	                        // 其它数据类型都当作字符串简单处理
	                        textValue = value.toString();
	                    }
	                    // 如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
	                    if (textValue != null) {
	                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
	                        Matcher matcher = p.matcher(textValue);
	                        if (matcher.matches()) {
	                            // 是数字当作double处理
	                            cell.setCellValue(Double.parseDouble(textValue));
	                        } else {
	                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
	                            cell.setCellValue(richString);
	                        }
	                    }
	                    beginCol++;
	            }

	        }
        
    	 } catch (SecurityException e) {
             // TODO Auto-generated catch block
    		 log.error(e.getMessage(),e);
         } catch (IllegalArgumentException e) {
             // TODO Auto-generated catch block
        	 log.error(e.getMessage(),e);
         } finally {
        	 //资源在外面释放
         }
    }
    
    /**
     * 合并指定列里值相同的行(纵向合并).
     * @param book 
     * @param colArr 指定哪些列需要合并，从1开始
     * @param beginRow 从哪行开始,从1开始，如果不指定，系统默认为1
     * @param endRow 到哪行结束，如果不指定，系统到数据的最后一行
     * @param sheet 
     */
    public static void mergeTheSameRows(HSSFWorkbook book , int[] colArr, Integer beginRow, Integer endRow, HSSFSheet sheet){
    	if(sheet == null){
    		sheet =  book.getSheetAt(0);
    	}
    	if(sheet == null){
    		return;
    	}
    	int totalRow = sheet.getLastRowNum();
    	int colNum = 0;
    	HSSFRow row = null;
    	HSSFCell cell = null;
    	int sameBeginRowStart = 0;//相同列的开始行数
    	int sameBeginRowEnd = 0;//相同列的结束行数
    	if(beginRow ==null){
    		beginRow = 1;
    	}
    	if(endRow == null){
    		endRow = totalRow+1;
    	}
    	for(int i=0; i<colArr.length;i++){
    		sameBeginRowStart = beginRow;
    		String lastContent = null;
        	String nowContent = null;
    		for(int rowNum=beginRow;rowNum<=endRow;rowNum++){
    			colNum = colArr[i];
    			row = sheet.getRow(rowNum-1);
    			if(row == null){
    				break;
    			}
    			cell = row.getCell(colNum-1);
    			/*if(cell == null){
    				continue;
    			}*/
    			if(lastContent == null){
    				lastContent = StringUtil.nullToEmpty(cell.getStringCellValue());
    				sameBeginRowStart = rowNum;
    			}else{
    				nowContent = StringUtil.nullToEmpty(cell.getStringCellValue());
    				sameBeginRowEnd = rowNum;
    				if(lastContent.equals(nowContent)){
    				}else{
    					if(sameBeginRowStart != sameBeginRowEnd-1){//需要做合并操作
    						merge(book, sameBeginRowStart, sameBeginRowEnd-1, colNum, colNum,sheet);
    					}
    					sameBeginRowStart = rowNum;
    				}
    				lastContent = StringUtil.nullToEmpty(cell.getStringCellValue());
    			}
        	}
    	}		
    }
    
    /**
     * 隐藏制定的列.
     * @param book 
     * @param colArr 指定隐藏哪些列，从1开始
     * @param sheet 
     */
    public static void hideColumns(HSSFWorkbook book , int[] colArr, HSSFSheet sheet){
    	if(sheet == null){
    		sheet =  book.getSheetAt(0);
    	}
    	if(sheet == null){
    		return;
    	}
    	for(int i=0; i<colArr.length;i++){
    		sheet.setColumnHidden(colArr[i]-1, true);
    	}		
    }
    
    /**
     * 从excel文件中读取里面的内容(以headers为基准) .xls
     * @param file excel文件
     * @param headers 标题code的数组
     * @param beginRow 起始行,从1开始
     * @param beginCol 起始列,从1开始
     * @return 读取的结果集
     * @throws Exception 异常
     */
    public static List<Map<String,Object>> readExcel(MultipartFile file,String[] headers,Integer beginRow,Integer beginCol) throws Exception{
    	return readExcel( file, headers, beginRow, beginCol,null);
    }
    
    public static List<Map<String,Object>> readExcel(MultipartFile file,String[] headers,Integer beginRow,Integer beginCol,String sheetName) throws Exception{
    	return readExcel(file.getInputStream(),headers,beginRow,beginCol,sheetName);
    }
    
    public static List<Map<String,Object>> readExcel(InputStream inputStream,String[] headers,Integer beginRow,Integer beginCol) throws Exception{
    	return readExcel(inputStream, headers, beginRow, beginCol,null);
    }
    
    /**
     * 从excel文件中读取里面的内容(以headers为基准) .xls
     * @param file excel文件
     * @param headers 标题code的数组
     * @param beginRow 起始行,从1开始
     * @param beginCol 起始列,从1开始
     * @param sheetName 要读取哪个sheet，如果为空，那么就取第一个
     * @return 读取的结果集
     * @throws Exception 异常
     */
    public static List<Map<String,Object>> readExcel(InputStream inputStream,String[] headers,Integer beginRow,Integer beginCol,String sheetName) throws Exception{
    	List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
    	InputStream is = null;
    	HSSFWorkbook workBook = null;
    	try{
	    	is =inputStream;
	    	workBook = new HSSFWorkbook(is);
	    	HSSFSheet sheet = null;
	    	if(!StringUtil.isEmpty(sheetName)){
	    		sheet = workBook.getSheet(sheetName);
	    		if(sheet == null){
	    			throw new Exception("该Excel未找到名字为【"+sheetName+"】的sheet！");
	    		}
	    	}else{
	    		sheet = workBook.getSheetAt(0);
	    	}
	    	if(beginRow == null){
	    		beginRow = 0;
	    	}else{
	    		beginRow = beginRow - 1;
	    	}
	    	if(beginCol == null){
	    		beginCol = 0;
	    	}else{
	    		beginCol = beginCol - 1;
	    	}
	    	
	    	HSSFRow row = null;
	    	HSSFCell cell = null;
	    	Map<String,Object> rowMap = null;
	    	Integer nowCol = null;
	    	int cellType = 0;
	    	Object value = null;
	    	for(int i=beginRow;i<=sheet.getLastRowNum();i++){
	    		row = sheet.getRow(i);
	    		if(row == null){
	    			continue;
	    		}
	    		rowMap = new HashMap<String,Object>();
	    		nowCol = beginCol;
	    		for(int j=0;j<headers.length;j++){
	    			cell = row.getCell(nowCol);
	    			if(cell == null){
	    				continue;
	    			}
	    			cellType = cell.getCellType();//单元格类型
	    			if(HSSFCell.CELL_TYPE_BLANK == cellType){
	    				value = null;
	    			}else if(HSSFCell.CELL_TYPE_BOOLEAN == cellType){
	    				value = cell.getBooleanCellValue();
	    			}else if(HSSFCell.CELL_TYPE_ERROR == cellType){
	    				value = cell.getErrorCellValue();
	    			}else if(HSSFCell.CELL_TYPE_FORMULA == cellType){
	    				if(HSSFDateUtil.isCellDateFormatted(cell)){//日期类型
	    					value = cell.getDateCellValue();
	    				}else{//数字类型
	    					value = cell.getNumericCellValue();
	    				}
	    				value = cell.getNumericCellValue();
	    			}else if(HSSFCell.CELL_TYPE_NUMERIC == cellType){
	    				value = cell.getNumericCellValue();
	    				if(value != null){
	    					value = df.format(value);
	    				}
	    			}else if(HSSFCell.CELL_TYPE_STRING == cellType){
	    				value =cell.getStringCellValue();
	    			}else{
	    				
	    				throw new Exception("Excel的单元格（第"+row.getRowNum()+"行，第"+cell.getColumnIndex()+"列）不能识别类型！");
	    			}
	    			rowMap.put(headers[j], value);
	    			nowCol++;
	    		}
	    		rowMap.put("SEQ_EXCEL", i+1);//excel的当前行号
	    		rsList.add(rowMap);
	    	}
    	}catch(Exception e){
    		throw e;
    	}finally{
    		if(workBook != null){
    			try{
    				//workBook.close();
    			}catch(Exception e){
    				log.error(e);
    			}
    		}
    		if(is != null){
    			try{
    				is.close();
    			}catch(Exception e){
    				log.error(e);
    			}
    		}
    	}
    	return rsList;
    }
    
    /**
     * 从*.xlsx 文件中读取里面的内容 并封装为list返回
     * @param file excel文件
     * @param index 需要获取列下标数组
     * @param index 需要获取列名字
     * @param beginRow 起始行,从0开始
     * @return
     * @throws Exception 
     */ 
    public static List<Map<String,Object>> readExcelXlsx(MultipartFile file,Integer[] index,String [] title,Integer beginRow) throws Exception{
    	InputStream is = null;
    	is =file.getInputStream();
    	XSSFWorkbook xssfWorkbook = null;
    	int cellType = 0;
    	Object value = null;
    	Map<String,Object> rowMap = null;
    	List<Map<String,Object>> rsList = new ArrayList<Map<String,Object>>();
    	try{
    		is =file.getInputStream();
    		xssfWorkbook = new XSSFWorkbook(is);
    		//获取第一个工作页
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
            if(beginRow==null){
            	beginRow = 1; //默认 从第二行开始 (下标从0开始)
            }
            // 获取当前工作薄的每一行
            for (int rowNum = beginRow; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
                XSSFRow xssfRow = xssfSheet.getRow(rowNum);
                rowMap = new HashMap<String,Object>();//重新实例化map
                if (xssfRow != null) {
                	for(int i=0;i<index.length;i++){
                		XSSFCell cell = xssfRow.getCell(index[i]);//获取值
                		if(cell == null){
                			continue;
                			
        	    		}
                		cellType = cell.getCellType();//单元格类型
                		if(HSSFCell.CELL_TYPE_BLANK == cellType){
    	    				value = null;
    	    			}else if(HSSFCell.CELL_TYPE_BOOLEAN == cellType){
    	    				value = cell.getBooleanCellValue();
    	    			}else if(HSSFCell.CELL_TYPE_ERROR == cellType){
    	    				value = cell.getErrorCellValue();
    	    			}else if(HSSFCell.CELL_TYPE_FORMULA == cellType){
    	    				if(HSSFDateUtil.isCellDateFormatted(cell)){//日期类型
    	    					value = cell.getDateCellValue();
    	    				}else{//数字类型
    	    					value = cell.getNumericCellValue();
    	    				}
    	    				value = cell.getNumericCellValue();
    	    			}else if(HSSFCell.CELL_TYPE_NUMERIC == cellType){
    	    				value = cell.getNumericCellValue();
    	    			}else if(HSSFCell.CELL_TYPE_STRING == cellType){
    	    				value =cell.getStringCellValue();
    	    			}else{
    	    				throw new Exception("Excel的单元格（第"+rowNum+"行，第"+index[i]+"列）不能识别类型！");
    	    			}
                		rowMap.put(title[i], value);//行 封装为map对象
                	}
                }if(rowMap.size()==index.length){
        		rsList.add(rowMap);//添加获取到的值
                }
            }
            
            return rsList;
        
    	}catch(Exception e){
    				throw e;
    		}finally{
    		if(xssfWorkbook != null){
    			try{
    				//xssfWorkbook.close();
    			}catch(Exception e){
    				log.error(e);
    			}
    		}
    		if(is != null){
    			try{
    				is.close();
    			}catch(Exception e){
    				log.error(e);
    			}
    		}
    	}
    }
}
