package com.trm.comm;

import java.sql.SQLException;

import com.dream.bizsdk.common.blc.DBBLC;
import com.dream.bizsdk.common.databus.BizData;

public class loginWelcomeBLC extends DBBLC {
	public int sWelcomeInfo(BizData rd, BizData sd) throws SQLException {
    	String roleID = sd.getString("USERROLEST");
		String sql ="";
					if(roleID.indexOf("kuaiji")!=-1){//财务 
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_dj_jhhotel) a, \n" +
								"(select tid cttid from ta_dj_jhct) b, \n" +
								"(select tid cltid from ta_dj_jhcl) c, \n" +
								"(select tid djtid from ta_dj_jhdj) d, \n" +
								"(select tid dytid from ta_dj_jhdy) e, \n" +
								"(select tid gwtid from ta_dj_jhgw) f, \n" +
								"(select tid jdtid from ta_dj_jhjd) g, \n" +
								"(select tid jiadtid from ta_dj_jhjiad) h, \n" +
								"(select tid pwtid from ta_dj_jhpw) i, \n" +
								"ta_dj_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(3,5) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_zt_jhhotel) a, \n" +
								"(select tid cttid from ta_zt_jhct) b, \n" +
								"(select tid cltid from ta_zt_jhcl) c, \n" +
								"(select tid djtid from ta_zt_jhdj) d, \n" +
								"(select tid dytid from ta_zt_jhdy) e, \n" +
								"(select tid gwtid from ta_zt_jhgw) f, \n" +
								"(select tid jdtid from ta_zt_jhjd) g, \n" +
								"(select tid jiadtid from ta_zt_jhjiad) h, \n" +
								"(select tid pwtid from ta_zt_jhpw) i, \n" +
								"ta_zt_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(3,5) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
					}else if(roleID.indexOf("djbzg")!=-1){//地接部主管
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_dj_jhhotel) a, \n" +
								"(select tid cttid from ta_dj_jhct) b, \n" +
								"(select tid cltid from ta_dj_jhcl) c, \n" +
								"(select tid djtid from ta_dj_jhdj) d, \n" +
								"(select tid dytid from ta_dj_jhdy) e, \n" +
								"(select tid gwtid from ta_dj_jhgw) f, \n" +
								"(select tid jdtid from ta_dj_jhjd) g, \n" +
								"(select tid jiadtid from ta_dj_jhjiad) h, \n" +
								"(select tid pwtid from ta_dj_jhpw) i, \n" +
								"ta_dj_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(3,5) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
					}else if(roleID.indexOf("transferForDj")!=-1){//地接计调
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_dj_jhhotel) a, \n" +
								"(select tid cttid from ta_dj_jhct) b, \n" +
								"(select tid cltid from ta_dj_jhcl) c, \n" +
								"(select tid djtid from ta_dj_jhdj) d, \n" +
								"(select tid dytid from ta_dj_jhdy) e, \n" +
								"(select tid gwtid from ta_dj_jhgw) f, \n" +
								"(select tid jdtid from ta_dj_jhjd) g, \n" +
								"(select tid jiadtid from ta_dj_jhjiad) h, \n" +
								"(select tid pwtid from ta_dj_jhpw) i, \n" +
								"ta_dj_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(1,2) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
					}else  if(roleID.indexOf("businessForDj")!=-1){//地接部业务员 
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_dj_jhhotel) a, \n" +
								"(select tid cttid from ta_dj_jhct) b, \n" +
								"(select tid cltid from ta_dj_jhcl) c, \n" +
								"(select tid djtid from ta_dj_jhdj) d, \n" +
								"(select tid dytid from ta_dj_jhdy) e, \n" +
								"(select tid gwtid from ta_dj_jhgw) f, \n" +
								"(select tid jdtid from ta_dj_jhjd) g, \n" +
								"(select tid jiadtid from ta_dj_jhjiad) h, \n" +
								"(select tid pwtid from ta_dj_jhpw) i, \n" +
								"ta_dj_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(1,2) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
					}else if(roleID.indexOf("ztbzg")!=-1){//组团部主管
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_zt_jhhotel) a, \n" +
								"(select tid cttid from ta_zt_jhct) b, \n" +
								"(select tid cltid from ta_zt_jhcl) c, \n" +
								"(select tid djtid from ta_zt_jhdj) d, \n" +
								"(select tid dytid from ta_zt_jhdy) e, \n" +
								"(select tid gwtid from ta_zt_jhgw) f, \n" +
								"(select tid jdtid from ta_zt_jhjd) g, \n" +
								"(select tid jiadtid from ta_zt_jhjiad) h, \n" +
								"(select tid pwtid from ta_zt_jhpw) i, \n" +
								"ta_zt_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(3,5) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
					}else if(roleID.indexOf("transferForZt")!=-1){//组团部计调
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_zt_jhhotel) a, \n" +
								"(select tid cttid from ta_zt_jhct) b, \n" +
								"(select tid cltid from ta_zt_jhcl) c, \n" +
								"(select tid djtid from ta_zt_jhdj) d, \n" +
								"(select tid dytid from ta_zt_jhdy) e, \n" +
								"(select tid gwtid from ta_zt_jhgw) f, \n" +
								"(select tid jdtid from ta_zt_jhjd) g, \n" +
								"(select tid jiadtid from ta_zt_jhjiad) h, \n" +
								"(select tid pwtid from ta_zt_jhpw) i, \n" +
								"ta_zt_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(1,2) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
					}else  if(roleID.indexOf("businessForZt")!=-1){//组团部业务员 
						sql ="select distinct gr.id,gr.state,gr.gw,gr.jd,a.hoteltid,b.cttid,c.cltid,d.djtid,e.dytid,f.gwtid,g.jdtid,h.jiadtid,i.pwtid \n" +
								" from \n" +
								"(select tid hoteltid from ta_zt_jhhotel) a, \n" +
								"(select tid cttid from ta_zt_jhct) b, \n" +
								"(select tid cltid from ta_zt_jhcl) c, \n" +
								"(select tid djtid from ta_zt_jhdj) d, \n" +
								"(select tid dytid from ta_zt_jhdy) e, \n" +
								"(select tid gwtid from ta_zt_jhgw) f, \n" +
								"(select tid jdtid from ta_zt_jhjd) g, \n" +
								"(select tid jiadtid from ta_zt_jhjiad) h, \n" +
								"(select tid pwtid from ta_zt_jhpw) i, \n" +
								"ta_zt_group gr \n"+
								"where 1=1 \n"+
								"and gr.state in(1,2) \n"+
								"and gr.id=a.hoteltid(+) \n" +
								"and gr.id=b.cttid(+) \n" +
								"and gr.id=c.cltid(+) \n" +
								"and gr.id=d.djtid(+) \n" +
								"and gr.id=e.dytid(+) \n" +
								"and gr.id=f.gwtid(+) \n" +
								"and gr.id=g.jdtid(+) \n" +
								"and gr.id=h.jiadtid(+) \n" +
								"and gr.id=i.pwtid(+) \n" +
								"order by gr.state";
						coreDAO.executeQuery(sql, "welcomeLogs",rd);
					}
		
    	return 999;
    }
}
