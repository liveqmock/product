package com.baseMng;

import java.sql.Connection;
import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;
import com.dream.bizsdk.common.util.string.PyUtil;

public class HotelBLC extends DBBLC{
	public HotelBLC(){
		this.entityName="TA_HOTEL";
	}
	/*
	 * 查询所有对应省市下的酒店
	 */
	public int getAllHotel(BizData rd,BizData sd)throws SQLException{
		String area_id=rd.getStringByDI("TA_HOTEL", "AREA_ID", 0);
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_HOTEL", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_HOTEL", "pageSize"));
		String orgid = sd.getString("orgid");
		String sql="select a.hotel_id,hotel_name,a.HOTEL_ADDRESS,a.HOTEL_TEL," +
				"a.HOTEL_BUSSINESS,a.HOTEL_BUSSINESS_TEL,b.dmsm1 hotel_level from TA_HOTEL a,DMSM b where b.dmlb=6 and a.hotel_level=b.dmz and a.orgid='"+orgid+"'";
		this.coreDAO.executeQueryPage(sql, "TA_HOTELs", pageNO, pageSize, rd);
		return 1;
	}
	public int getHotelByName(BizData rd,BizData sd)throws SQLException{
		String name=rd.getString("CMPNY_NAME");
		String area_id=rd.getString("area_id");
	//	String ywlb = rd.getString("ywlb");
		String orgid = sd.getString("orgid");
		int pageNO=Integer.parseInt(rd.getStringAttr("TA_HOTEL", "pageNO"));
		int pageSize=Integer.parseInt(rd.getStringAttr("TA_HOTEL", "pageSize"));
		String sql="select a.hotel_id,hotel_name,a.HOTEL_ADDRESS,a.HOTEL_TEL," +
				"a.HOTEL_BUSSINESS,a.HOTEL_BUSSINESS_TEL,b.dmsm1 hotel_level from " +
				"TA_HOTEL a,DMSM b where a.area_id="+area_id+" and a.hotel_name like '%"+name+"%' and b.dmlb=6 and a.hotel_level=b.dmz and a.orgid='"+orgid+"'";
		this.coreDAO.executeQueryPage(sql, "TA_HOTELs", pageNO, pageSize, rd);
		rd.add("TA_HOTEL", "area_id",area_id);
		return 1;
	}
	/*
	 * 根据ID查询酒店信息
	 */
	public int getHotelById(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		String sql="select * from TA_HOTEL a,DMSM b where a.hotel_id="+id+" and b.dmlb=6 and a.hotel_level=b.dmz";
		this.coreDAO.executeQuery(sql,"hotelInfo",rd);
		String sql2="select b.FILEID,b.FILEURL from ta_picshare a,drmfileuploadlog b where a.picForWhere=1 and  a.relating_id="+id+" and a.fileUploadID=b.fileid";
		coreDAO.executeQuery(sql2, "hotelImg", rd);
		String sql3="select a.hPriceID,a.hotelID,a.priceName,a.hPrice from TA_HOTELPRICE a where a.hotelID="+id;
		coreDAO.executeQuery(sql3, "hotelPrice", rd);
		
		StringBuffer sql4 = new StringBuffer();
		// 根据城市ID查询城市名称
		sql4.append("select t.*,x.name,x.pid,x.layer\n").append(
				"from TA_HOTEL t,xzqh x\n");
		sql4.append("where t.area_id=x.id(+)\n").append("and t.HOTEL_ID=")
				.append(id);
		coreDAO.executeQuery(sql4.toString(), "TA_HOTELs", rd);
		
		return 1;
	}
	/*
	 * 添加酒店
	 */
	public int addHotel(BizData rd,BizData sd)throws SQLException{
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			int h_id=this.queryMaxIDByPara("TA_HOTEL", "HOTEL_ID", null);
			rd.add("TA_HOTEL", "HOTEL_ID",h_id);
			int ImgRows[]=(int[]) rd.get("FILEID");
			if(null!=ImgRows){
				for(int a=0;a<ImgRows.length;a++){
					rd.add("ta_picShare", "PicID",this.queryMaxIDByPara("ta_picShare", "picID", null));
					rd.add("ta_picShare", "fileUploadID",ImgRows[a]);
					rd.add("ta_picShare", "relating_id",h_id);
					rd.add("ta_picShare", "picForWhere",rd.getString("tplx"));
					this.coreDAO.insert("ta_picShare", rd, conn);
				}
			}
			String priceRows[]=rd.getRowIDs("TA_HOTELPRICE");
			for(int p=0;p<priceRows.length;p++){
			int price_id=this.queryMaxIDByPara("TA_HOTELPRICE","HPRICEID", null);
			rd.add("TA_HOTELPRICE", "HOTELID",p,h_id);
			rd.add("TA_HOTELPRICE","HPRICEID",p,price_id);
			}
			this.coreDAO.insert("TA_HOTELPRICE", rd, conn);
			
			//生成拼音码
			String cmpName = rd.getString("TA_HOTEL", "HOTEL_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_HOTEL", "NAMECODE", 0, nameCode);
			rd.add("TA_HOTEL", "ORGID", 0, sd.getString("orgid"));
			this.coreDAO.insert("TA_HOTEL",rd,conn);
			rd.add("area_id",rd.getStringByDI("TA_HOTEL", "area_id", 0));
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if((null!=conn)){
				conn.close();
			}
		}
		return 1;
	}
	/*修改酒店信息*/
	public int upHotel(BizData rd,BizData sd)throws SQLException{
		String hotel_id=rd.getStringByDI("TA_HOTEL", "HOTEL_ID", 0);
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		int[] imgRows=(int[]) rd.get("FILEID");
		try{
			//添加新的图片
			if(null!=imgRows){
				for(int a=0;a<imgRows.length;a++){
					rd.add("ta_picShare", "PicID",this.queryMaxIDByPara("ta_picShare", "picID", null));
					rd.add("ta_picShare", "fileUploadID",imgRows[a]);
					rd.add("ta_picShare", "relating_id",rd.getStringByDI("TA_HOTEL", "HOTEL_ID", 0));
					rd.add("ta_picShare", "picForWhere",rd.getString("tplx"));
					this.coreDAO.insert("ta_picShare", rd, conn);
				}
			}
			//删除价格
			String sql="delete from TA_HOTELPRICE a where a.hotelID="+hotel_id;
			coreDAO.executeUpdate(sql, conn);
			//添加价格
			String priceRows[]=rd.getRowIDs("TA_HOTELPRICE");
			for(int p=0;p<priceRows.length;p++){
				int id=this.queryMaxIDByPara("TA_HOTELPRICE", "hPriceID", null);
				rd.add("TA_HOTELPRICE","hPriceID",p,id);
				rd.add("TA_HOTELPRICE","HOTELID",p,hotel_id);
			}
			coreDAO.insert("TA_HOTELPRICE", rd, conn);
			//修改
			//生成拼音码
			String cmpName = rd.getString("TA_HOTEL", "HOTEL_NAME", 0);
			String nameCode = PyUtil.get1stLetterOf4Chars(cmpName);
			rd.add("TA_HOTEL", "NAMECODE", 0, nameCode);
			this.coreDAO.update("TA_HOTEL",rd,conn);
			rd.add("area_id", rd.getString("area_id"));
			this.coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
	}
	/*
	 * 删除酒店信息
	 */
	public int delHotel(BizData rd,BizData sd)throws SQLException{
		
		rd.add("ywlb", rd.getString("ywlb"));
		Connection conn=coreDAO.getConnection();
		String hid=rd.getString("HOTEL_ID");
		coreDAO.beginTrasct(conn);
		try{
			String sql="select a.fileUploadID from ta_picShare a where a.picForWhere=1 and a.relating_id="+hid;
			coreDAO.executeQuery(sql, "rsHotelImg", rd);
			int rows = rd.getTableRowsCount("rsHotelImg");
			if(rows>0){
				for(int a=0;a<rows;a++){
					String sql2="delete from drmfileuploadlog where FILEID="+rd.getStringByDI("rsHotelImg", "fileUploadID", a);
					this.coreDAO.executeUpdate(sql2,conn);
				}
			}         //删除drmfileuploadlog 酒店照片
			String sql3="delete from ta_picShare where picForWhere=1 and relating_id="+hid;
			this.coreDAO.executeUpdate(sql3,conn);  //删除TA_HOTEL_PIC表
			String sql4="delete from TA_HOTEL where hotel_id="+hid;
			//删除酒店价格
			String sql5="delete from TA_HOTELPRICE a where a.HPRICEID="+hid;
			coreDAO.executeUpdate(sql5, conn);
			//删除TA_HOTEL表
			coreDAO.executeUpdate(sql4,conn);
			rd.add("area_id", rd.getString("area_id"));
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
	}
	/*批量删除*/
	public int batchDelHotel(BizData rd,BizData sd)throws SQLException{
		
		rd.add("ywlb", rd.getString("ywlb"));
		String rows[]=rd.getRowIDs("HOTEL");
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			for(int a=0;a<rows.length;a++){
				String sql="select a.fileUploadID from ta_picShare a where a.picForWhere=1 and a.relating_id="+rd.getString("HOTEL","CHECKBOX",rows[a]);
				coreDAO.executeQuery(sql, "rsHotelImg", rd);
				int imgRows = rd.getTableRowsCount("rsHotelImg");
					for(int b=0;b<imgRows;b++){
						String sql2="delete from drmfileuploadlog where FILEID="+rd.getStringByDI("rsHotelImg", "fileUploadID", b);
						this.coreDAO.executeUpdate(sql2,conn);
					}
				        //删除drmfileuploadlog 酒店照片
				String sql3="delete from ta_picShare where picForWhere=1 and relating_id="+rd.getString("HOTEL","CHECKBOX",rows[a]);
				this.coreDAO.executeUpdate(sql3,conn);  //删除TA_HOTEL_PIC表
				String sql4="delete from TA_HOTEL where hotel_id="+rd.getString("HOTEL","CHECKBOX",rows[a]);
				//删除价格表
				String sql5="delete from TA_HOTELPRICE a where a.HPRICEID="+rd.getString("HOTEL","CHECKBOX",rows[a]);
				coreDAO.executeUpdate(sql5, conn);
				//删除TA_HOTEL表
				coreDAO.executeUpdate(sql4,conn);
				rd.remove("rsHotelImg");
			}
		rd.add("area_id", rd.getString("area_id"));
		coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		return 1;
		
	}
	public int delImg(BizData rd,BizData sd)throws SQLException{
		String id=rd.getString("id");
		Connection conn=coreDAO.getConnection();
		coreDAO.beginTrasct(conn);
		try{
			String sql="delete from TA_PICSHARE a where a.FILEUPLOADID="+id;
			coreDAO.executeUpdate(sql, conn);
			String sql2="delete from DRMFILEUPLOADLOG a where a.fileid="+id;
			coreDAO.executeUpdate(sql2,conn);
			coreDAO.commitTrasct(conn);
		}catch(Exception e){
			coreDAO.rollbackTrasct(conn);
		}finally{
			if(null!=conn){
				conn.close();
			}
		}
		
		return 1;
	}
}
