/**
 *all rights reserved,@copyright 2003
 */
package com.dream.bizsdk.common.util.jpg;

import java.sql.Date;
import java.util.Vector;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.dream.bizsdk.common.databus.BizData;


/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2004-3-8
 * Time: 12:50:26
 */
public class JPGGenerator {

    public JFreeChart makeJPG(BizData imageData) {
        JFreeChart chart = null;
        String type = (String) imageData.get("imageType");
        if (type != null) {
            if (type.compareToIgnoreCase("pie") == 0) {
                chart = makePieChart(imageData);
            } else if (type.compareToIgnoreCase("pie3d") == 0) {
                chart = makePie3dChart(imageData);
            } else if (type.compareToIgnoreCase("line") == 0) {
                chart = makeLineChart(imageData);
            } else if (type.compareToIgnoreCase("bar") == 0) {
                chart = makeBarChart(imageData);
            } else if (type.compareToIgnoreCase("xystep") == 0) {
                chart = makeXYStepChart(imageData);
            } else if (type.compareToIgnoreCase("xybar") == 0) {
                chart = makeXYBarChart(imageData);
            }
        }
        return chart;
    }

    /**
     * make a float bie chart;
     *
     * @param imageData
     * @return
     */
    private JFreeChart makePieChart(BizData imageData) {
        int count = 0;
        int i = 0;
        String title = null;

        double value = 0;
        String dim1 = null;
        JFreeChart chart = null;
        DefaultPieDataset dpd = new DefaultPieDataset();
        count = imageData.getTableRowsCount("ImageData");
        while (i < count) {
            String row = new Integer(i).toString();
            value = ((Double) imageData.get("ImageData", "subject", row)).doubleValue();
            dim1 = imageData.getString("ImageData", "dim1", row);
            dpd.setValue(dim1, value);
            i++;
        }
        title = imageData.getString("title");
        chart = ChartFactory.createPieChart(title, dpd, true, true, true);
        return chart;
    }

    /**
     * make a 3d bie chart;
     *
     * @param imageData
     * @return
     */
    private JFreeChart makePie3dChart(BizData imageData) {
        int count = 0;
        int i = 0;
        String title = null;

        double value = 0;
        String dim1 = null;
        JFreeChart chart = null;
        DefaultPieDataset dpd = new DefaultPieDataset();
        count = imageData.getTableRowsCount("ImageData");
        while (i < count) {
            String row = new Integer(i).toString();
            value = ((Double) imageData.get("ImageData", "subject", row)).doubleValue();
            dim1 = imageData.getString("ImageData", "dim1", row);
            dpd.setValue(dim1, value);
            i++;
        }
        title = imageData.getString("title");
        chart = ChartFactory.createPieChart3D(title, dpd, true, true, true);
        return chart;
    }

    private JFreeChart makeBarChart(BizData imageData) {
        int count = 0;
        int i = 0;
        String title = null;

        Comparable zVal = null;
        Comparable xVal = null;
        JFreeChart chart = null;
        String sName = imageData.getString("subjectName");
        String dim1Name = imageData.getString("dim1Name");
        DefaultCategoryDataset dpd = new DefaultCategoryDataset();
        count = imageData.getTableRowsCount("ImageData");
        String type2 = (String) imageData.get("dim2Type");
        String type1 = (String) imageData.get("dim1Type");
        while (i < count) {
            String row = new Integer(i).toString();
            double y = ((Double) imageData.get("ImageData", "subject", row)).doubleValue();
            if (type1.compareToIgnoreCase("string") == 0) {
                xVal = imageData.getString("ImageData", "dim1", row);
            } else if (type1.compareToIgnoreCase("number") == 0) {
                xVal = new Double(imageData.getDouble("ImageData", "dim1", row));
            } else if (type1.compareToIgnoreCase("date") == 0 || type2.compareToIgnoreCase("time") == 0) {
                xVal = imageData.getDate("ImageData", "dim1", row);
            } else {
                xVal = imageData.getString("ImageData", "dim1", row);
            }
            //if there is only one dimension, then there is only one line will be show;
            if (type2.compareToIgnoreCase("string") == 0) {
                zVal = imageData.getString("ImageData", "dim2", row);
            } else if (type2.compareToIgnoreCase("number") == 0) {
                zVal = new Double(imageData.getDouble("ImageData", "dim2", row));
            } else if (type2.compareToIgnoreCase("date") == 0 || type2.compareToIgnoreCase("time") == 0) {
                zVal = imageData.getDate("ImageData", "dim2", row);
            } else {
                zVal = imageData.getString("ImageData", "dim2", row);
            }
            dpd.setValue(y, zVal, xVal);
            i++;
        }
        title = imageData.getString("title");
        chart = ChartFactory.createBarChart(title, dim1Name, sName, dpd, PlotOrientation.VERTICAL, true, true, true);
        return chart;
    }

    /**
     * @param imageData
     * @return
     */
    private JFreeChart makeLineChart(BizData imageData) {
        boolean newSeries = true;
        int i = 0;
        int len = 0;
        int count = 0;
        double x = 0;
        double y = 0;
        String title = null;

        Object zVal = null;
        Object oldZVal = null;


        JFreeChart chart = null;
        XYSeries xys = null;
        XYSeriesCollection xysc = new XYSeriesCollection();

        title = (String) imageData.get("title");
        String sName = imageData.getString("subjectName");
        String dim1Name = imageData.getString("dim1Name");
        String type2 = (String) imageData.get("dim2Type");

        count = ((Integer) imageData.get("dimsCount")).intValue();
        String[] rowIDs = imageData.getRowIDs("ImageData");
        len = rowIDs.length;

        while (i < len) {
            String row = new Integer(i).toString();
            y = ((Double) imageData.get("ImageData", "subject", row)).doubleValue();
            x = imageData.getDouble("ImageData", "dim1", row);
            //if there is only one dimension, then there is only one line will be show;
            if (count > 1) {
                zVal = imageData.get("ImageData", "dim2", row);
                if (zVal != null && oldZVal != null && !equal(zVal, oldZVal, type2)) {
                    newSeries = true;
                }
            }
            if (newSeries) {
                if (type2.compareToIgnoreCase("string") == 0) {
                    xys = new XYSeries((String) zVal);
                } else if (type2.compareToIgnoreCase("number") == 0) {
                    xys = new XYSeries(((Double) zVal).toString());
                } else if (type2.compareToIgnoreCase("date") == 0) {
                    xys = new XYSeries(BizData.sdfDate.format((Date) zVal));
                } else if (type2.compareToIgnoreCase("time") == 0) {
                    xys = new XYSeries(BizData.sdfTime.format((Date) zVal));
                } else {
                    xys = new XYSeries(zVal.toString());
                }
                xysc.addSeries(xys);
                oldZVal = zVal;
                newSeries = false;
            }
            xys.add(x, y);
            i++;
        }
        chart = ChartFactory.createXYLineChart(title, dim1Name, sName, xysc, PlotOrientation.VERTICAL, true, true, true);

        return chart;
    }


    private JFreeChart makeXYStepChart(BizData imageData) {
        boolean newSeries = true;
        int i = 0;
        int len = 0;
        int count = 0;
        double x = 0;
        double y = 0;
        String title = null;

        Object zVal = null;
        Object oldZVal = null;


        JFreeChart chart = null;
        XYSeries xys = null;
        XYSeriesCollection xysc = new XYSeriesCollection();

        title = (String) imageData.get("title");
        String sName = imageData.getString("subjectName");
        String dim1Name = imageData.getString("dim1Name");
        String type2 = (String) imageData.get("dim2Type");

        count = ((Integer) imageData.get("dimsCount")).intValue();
        String[] rowIDs = imageData.getRowIDs("ImageData");
        len = rowIDs.length;

        while (i < len) {
            String row = new Integer(i).toString();
            y = ((Double) imageData.get("ImageData", "subject", row)).doubleValue();
            x = imageData.getDouble("ImageData", "dim1", row);
            //if there is only one dimension, then there is only one line will be show;
            if (count > 1) {
                zVal = imageData.get("ImageData", "dim2", row);
                if (zVal != null && oldZVal != null && !equal(zVal, oldZVal, type2)) {
                    newSeries = true;
                }
            }
            if (newSeries) {
                if (type2.compareToIgnoreCase("string") == 0) {
                    xys = new XYSeries((String) zVal);
                } else if (type2.compareToIgnoreCase("number") == 0) {
                    xys = new XYSeries(((Double) zVal).toString());
                } else if (type2.compareToIgnoreCase("date") == 0) {
                    xys = new XYSeries(BizData.sdfDate.format((Date) zVal));
                } else if (type2.compareToIgnoreCase("time") == 0) {
                    xys = new XYSeries(BizData.sdfTime.format((Date) zVal));
                } else {
                    xys = new XYSeries(zVal.toString());
                }
                xysc.addSeries(xys);
                oldZVal = zVal;
                newSeries = false;
            }
            xys.add(x, y);
            i++;
        }
        chart = ChartFactory.createXYStepChart(title, dim1Name, sName, xysc, PlotOrientation.VERTICAL, true, true, true);

        return chart;
    }

    private JFreeChart makeXYBarChart(BizData imageData) {
        boolean newSeries = true;
        int i = 0;
        int len = 0;
        int count = 0;
        int k = 0;
        double x = 0;
        double y = 0;
        String title = null;

        Object zVal = null;
        Object oldZVal = null;


        JFreeChart chart = null;
        Vector xys = new Vector();
        HistogramDataset xysc = new HistogramDataset();

        title = (String) imageData.get("title");
        String sName = imageData.getString("subjectName");
        String dim1Name = imageData.getString("dim1Name");
        String type2 = (String) imageData.get("dim2Type");

        count = ((Integer) imageData.get("dimsCount")).intValue();
        String[] rowIDs = imageData.getRowIDs("ImageData");
        len = rowIDs.length;

        while (i < len) {
            String row = new Integer(i).toString();
            y = ((Double) imageData.get("ImageData", "subject", row)).doubleValue();
            x = imageData.getDouble("ImageData", "dim1", row);
            //if there is only one dimension, then there is only one line will be show;
            if (count > 1) {
                zVal = imageData.get("ImageData", "dim2", row);
                if (zVal != null && oldZVal != null && !equal(zVal, oldZVal, type2)) {
                    newSeries = true;
                }
            }
            if (newSeries) {
                if ((k = xys.size()) > 0) {
                    int j = 0;
                    double[] values = new double[k];
                    while (j < k) {
                        values[j] = ((Double) xys.elementAt(j)).doubleValue();
                        j++;
                    }
                    xysc.addSeries(oldZVal.toString(), values, values.length);
                }
                oldZVal = zVal;
                newSeries = false;
                xys.clear();
            }
            xys.add(new Double(x));
            xys.add(new Double(y));
            i++;
        }
        if ((k = xys.size()) > 0) {
            int j = 0;
            double[] values = new double[k];
            while (j < k) {
                values[j] = ((Double) xys.elementAt(j)).doubleValue();
                j++;
            }
            xysc.addSeries(oldZVal.toString(), values, values.length);
        }
        //chart = ChartFactory.createXYBarChart(title, dim1Name, sName, xysc, PlotOrientation.VERTICAL, true, true, true); edit by divine
        chart = ChartFactory.createXYBarChart(title, dim1Name, true, sName, xysc, PlotOrientation.VERTICAL, true, true, true);

        return chart;
    }

/*    private JFreeChart makeHistChart(BizData imageData){
        boolean newSeries=true;
        int i=0;
        int len=0;
        int count=0;
        double x=0;
        double y=0;
        String title=null;

        Object zVal = null;
        Object oldZVal = null;


        JFreeChart chart=null;
        HistogramDataset hisData = new HistogramDataset();
        XYSeries xys=null;
        XYSeriesCollection  xysc=new XYSeriesCollection();

        title = (String)imageData.get("title");
        String sName = imageData.getString("subjectName");
        String dim1Name = imageData.getString("dim1Name");
        String type2 = (String)imageData.get("dim2Type");

        count=((Integer)imageData.get("dimsCount")).intValue();
        String[] rowIDs=imageData.getRowIDs("ImageData");
        len = rowIDs.length;

        while(i<len){
            String row =new Integer(i).toString();
            y=((Double)imageData.get("ImageData","subject",row)).doubleValue();
            x=imageData.getDouble("ImageData","dim1",row);
            //if there is only one dimension, then there is only one line will be show;
            if(count>1){
                zVal=imageData.get("ImageData","dim2",row);
                if(zVal!=null && oldZVal!=null && !equal(zVal,oldZVal,type2)){
                    newSeries=true;
                }
            }
            if(newSeries){
                if(type2.compareToIgnoreCase("string")==0){
                    xys = new XYSeries((String)zVal);
                }else if(type2.compareToIgnoreCase("number")==0){
                    xys = new XYSeries(((Double)zVal).toString());
                }else if(type2.compareToIgnoreCase("date")==0){
                    xys = new XYSeries(BizData.sdfDate.format((Date)zVal));
                }else if(type2.compareToIgnoreCase("time")==0){
                    xys = new XYSeries(BizData.sdfTime.format((Date)zVal));
                }else{
                    xys= new  XYSeries(zVal.toString());
                }
                xysc.addSeries(xys);
                oldZVal=zVal;
                newSeries=false;
            }
            xys.add(x,y);
            i++;
        }
        chart = ChartFactory.createHistogram(title,dim1Name,sName,xysc,PlotOrientation.VERTICAL,true,true,true);

        return chart;
    }
*/
    private boolean equal(Object o1, Object o2, String type) {
        boolean e = false;
        try {
            if (type.compareToIgnoreCase("string") == 0) {
                if (((String) o1).compareTo((String) o2) == 0) {
                    e = true;
                }
            } else if (type.compareToIgnoreCase("number") == 0) {
                if (((Double) o1).compareTo((Double) o2) == 0) {
                    e = true;
                }
            } else if (type.compareToIgnoreCase("date") == 0 || type.compareToIgnoreCase("time") == 0) {
                if (((Date) o1).getTime() == ((Date) o2).getTime()) {
                    e = true;
                }
            }
        } catch (Exception e1) {

        }
        return e;
    }
}
