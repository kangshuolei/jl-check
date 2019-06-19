package com.hbsi.common.utils.word;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import com.hbsi.common.utils.StringUtils;

public class WordUtil
{
	public static CustomXWPFDocument doc = null;
	public static int m=0;
	/**
	 * 根据指定的参数值、模板，生成 word 文档
	 * @param param 需要替换的变量
	 * @param template 模板
	 */
	public static CustomXWPFDocument generateWord(Map<String, Object> param, String template)
	{
		try
		{
			OPCPackage pack = POIXMLDocument.openPackage(template);
			doc = new CustomXWPFDocument(pack);
			if (param != null && param.size() > 0)
			{
			
				// 处理段落
				List<XWPFParagraph> paragraphList = doc.getParagraphs();
				processParagraphs(paragraphList, param, doc);

				List<XWPFTable> tables = doc.getTables();
				for (int i = 0; i < tables.size(); i++)
				{
					XWPFTable xwpfTable = tables.get(i);
					String text = xwpfTable.getText();
					List<XWPFTableRow> rows = xwpfTable.getRows();
					for (XWPFTableRow row : rows)
					{
						List<XWPFTableCell> cells = row.getTableCells();
						for (XWPFTableCell cell : cells)
						{
							List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
							try
							{
								processParagraphs(paragraphListTable, param, doc);
							}
							catch (Exception e)
							{
							}
						}
					}
				}

			}
		}
		catch (Exception e)
		{
			System.out.println("出现异常。");
		}
		return doc;
	}

	/**
	 * 根据指定的参数值、模板，生成 word 文档
	 * @param param 需要替换的变量
	 * @param template 模板
	 */
	public static CustomXWPFDocument generateWord(Map<String, Object> param, CustomXWPFDocument doc)
	{
		try
		{
			if (param != null && param.size() > 0)
			{
				
				// 处理段落
				List<XWPFParagraph> paragraphList = doc.getParagraphs();
				processParagraphs(paragraphList, param, doc);

				List<XWPFTable> tables = doc.getTables();
				for (int i = 0; i < tables.size(); i++)
				{
					XWPFTable xwpfTable = tables.get(i);
//					String text = xwpfTable.getText();
					List<XWPFTableRow> rows = xwpfTable.getRows();
					for (XWPFTableRow row : rows)
					{
						List<XWPFTableCell> cells = row.getTableCells();
						for (XWPFTableCell cell : cells)
						{
							List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
							try
							{
							
								processParagraphs(paragraphListTable, param, doc);
							}
							catch (Exception e)
							{
								//e.printStackTrace();
							}
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("出现异常。");
		}
		return doc;
	}
	
	/**
	 * 根据指定的参数值、模板，生成 word 文档
	 * @param param 需要替换的变量
	 * @param template 模板
	 */
	public static CustomXWPFDocument removeMakers(CustomXWPFDocument doc)
	{
		try
		{		
				m=0;
				// 处理段落
				List<XWPFParagraph> paragraphList = doc.getParagraphs();
				processParagraphsImg(paragraphList,doc);
				
				List<XWPFTable> tables = doc.getTables();
				for (int i = 0; i < tables.size(); i++)
				{
					XWPFTable xwpfTable = tables.get(i);
					String text = xwpfTable.getText();
					List<XWPFTableRow> rows = xwpfTable.getRows();
					for (XWPFTableRow row : rows)
					{
						List<XWPFTableCell> cells = row.getTableCells();
						for (XWPFTableCell cell : cells)
						{
							List<XWPFParagraph> paragraphListTable = cell.getParagraphs();
							try
							{
								processParagraphsImg(paragraphListTable,doc);
							}
							catch (Exception e)
							{
								//e.printStackTrace();
							}
						}
					}
				}
		}
		catch (Exception e)
		{
			System.out.println("出现异常。");
		}
		return doc;
	}
	
	
	
	/**
	 * 处理段落
	 * @param paragraphList
	 */
	public static void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, Object> param, CustomXWPFDocument doc)
	{
		if (paragraphList != null && paragraphList.size() > 0)
		{
			for (XWPFParagraph paragraph : paragraphList)
			{
				// paragraph.setAlignment(ParagraphAlignment.LEFT);
//				String text = paragraph.getText();
//				System.out.println(text);
				List<XWPFRun> runs = paragraph.getRuns();
				String text=new String();
				for (XWPFRun run : runs)
				{
					text = run.getText(0);
					
					if (text != null&& text !="")
					{
						boolean isSetText = false;
						for (Entry<String, Object> entry : param.entrySet())
						{
							String key = entry.getKey();
//							System.out.println(key);
							if (text.equals(key))
							{
								isSetText = true;
								Object value = entry.getValue();
								if(value instanceof String&&StringUtils.isEmpty((String) value)||value==null){
									value="";
								}
								if(text.startsWith("img")&&value instanceof String){
									text = text.replace(key, "");
								}
								if (value instanceof String)
								{
									// 文本替换
									
									text = text.replace(key, value.toString());
//									System.out.println(text);
								}
								else if (value instanceof Map)
								{// 图片替换
									text = text.replace(key, "");
									Map pic = (Map) value;
									int width = Integer.parseInt(pic.get("width").toString());
									int height = Integer.parseInt(pic.get("height").toString());
									int picType = getPictureType(pic.get("type").toString());
									byte[] byteArray = (byte[]) pic.get("content");
									ByteArrayInputStream byteInputStream = new ByteArrayInputStream(byteArray);
									try
									{
										String ind = doc.addPictureData(byteInputStream,picType);
										int id =  doc.getNextPicNameNumber(picType);
										doc.createPicture(ind, id, width, height,paragraph); 
										//m=m+1;
										//doc.createPicture(doc.getAllPictures().size()-1,width, height, paragraph);
										
									}
									catch (Exception e)
									{
									}
								}
							}
						}
						
						if (isSetText)
						{
							String[] texts = text.split("[\\t\\n\\r]");
							if (texts.length > 0)
							{
								// paragraph.setAlignment(ParagraphAlignment.LEFT);
								for (int i = 0; i < texts.length; i++)
								{
									if (i == 0)
									{
										run.setText(texts[i].trim(), 0);
									}
									else
									{
										run.addBreak();
										run.setText(texts[i].trim());
									}

								}
							}
							else
							{
								run.setText(text, 0);
							}
						}
					}
				}
			}
		}
	}
	/**
	 * 处理段落
	 * @param paragraphList
	 */
	public static void processParagraphsImg(List<XWPFParagraph> paragraphList,CustomXWPFDocument doc)
	{
		if (paragraphList != null && paragraphList.size() > 0)
		{
			for (XWPFParagraph paragraph : paragraphList)
			{
				// paragraph.setAlignment(ParagraphAlignment.LEFT);
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs)
				{
					String text = run.getText(0);
					if (text != null)
					{
						if(text.contains("img_")||text.contains("text_")){
							text = text.replace(text, "");
							run.setText(text, 0);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 根据图片类型，取得对应的图片类型代码
	 * @param picType
	 * @return int
	 */
	public static int getPictureType(String picType)
	{
		int res = CustomXWPFDocument.PICTURE_TYPE_PICT;
		if (picType != null)
		{
			if (picType.equalsIgnoreCase("png"))
			{
				res = CustomXWPFDocument.PICTURE_TYPE_PNG;
			}
			else if (picType.equalsIgnoreCase("dib"))
			{
				res = CustomXWPFDocument.PICTURE_TYPE_DIB;
			}
			else if (picType.equalsIgnoreCase("emf"))
			{
				res = CustomXWPFDocument.PICTURE_TYPE_EMF;
			}
			else if (picType.equalsIgnoreCase("jpg") || picType.equalsIgnoreCase("jpeg"))
			{
				res = CustomXWPFDocument.PICTURE_TYPE_JPEG;
			}
			else if (picType.equalsIgnoreCase("wmf"))
			{
				res = CustomXWPFDocument.PICTURE_TYPE_WMF;
			}
		}
		return res;
	}

	/**
	 * 将输入流中的数据写入字节数组
	 * @param in
	 * @return
	 */
	public static byte[] inputStream2ByteArray(InputStream in, boolean isClose)
	{
		byte[] byteArray = null;
		try
		{
			int total = in.available();
			byteArray = new byte[total];
			in.read(byteArray);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (isClose)
			{
				try
				{
					in.close();
				}
				catch (Exception e2)
				{
					System.out.println("关闭流失败");
				}
			}
		}
		return byteArray;
	}

}
