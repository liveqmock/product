/**
 *all rights reserved,@copyright 2003
 */
package test;

import java.security.*;

/**
 * Description:
 * <p/>
 * User: chuguanghua
 * Date: 2005-3-28
 * Time: 14:22:34
 * Version: 1.0
 */
public class RSA {
	private void generateKeyPairIntoFile() throws Exception{
		KeyPairGenerator keygen=KeyPairGenerator.getInstance("RSA");
		SecureRandom secrand=new SecureRandom();
		secrand.setSeed("redtroy".getBytes());
		keygen.initialize(1024,secrand);
		KeyPair keys=keygen.generateKeyPair();
		PublicKey pubkey=keys.getPublic();
		PrivateKey prikey=keys.getPrivate();
		java.io.ObjectOutputStream out=new java.io.ObjectOutputStream(new java.io.FileOutputStream("e:/private.key"));
		out.writeObject(prikey);
        out.flush();
		out.close();
	    out=new java.io.ObjectOutputStream(new java.io.FileOutputStream("e:/public.key"));
	    out.writeObject(pubkey);
        out.flush();
	    out.close();
	}
	private PublicKey readPubKeyFromFile(String filepath) throws Exception{
		java.io.ObjectInputStream in=new java.io.ObjectInputStream(new java.io.FileInputStream("e:/public.key"));
		PublicKey pubkey=(PublicKey)in.readObject();
		in.close();
		return pubkey;
	}
	private PrivateKey readPriKeyFromFile(String filepath) throws Exception{
		java.io.ObjectInputStream in=new java.io.ObjectInputStream(new java.io.FileInputStream("e:/private.key"));
		PrivateKey prikey=(PrivateKey)in.readObject();
		in.close();
		return prikey;
	}
	private byte [] signDataByPriKey(String src,String prikeyfilepath) throws Exception{
		Signature sig = Signature.getInstance("MD5withRSA","BC");
		PrivateKey prikey=this.readPriKeyFromFile(prikeyfilepath);
		sig.initSign(prikey);
		sig.update(src.getBytes());
		byte [] signedinfo=sig.sign();
		return signedinfo;
	}
	private boolean checkSignedInfo(String src,byte[] sig,String pubkeyfilepath) throws Exception{
		  Signature signetcheck = Signature.getInstance("MD5withRSA","BC");
		  PublicKey pubkey=this.readPubKeyFromFile(pubkeyfilepath);
		  signetcheck.initVerify(pubkey);
		  signetcheck.update(src.getBytes());
		  if (signetcheck.verify(sig)){
		  	return true;
		  }else{
		  	return false;
		  }
	}
	public static void main(String args[]){
//        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//		RSA rsa=new RSA();
//		String string="POSID=000000000&BRANCHID=110000000&ORDERID=19991101234&PAYMENT=500.00&CURCODE=01&REMARK1£½19991101&REMARK2=merchantname&SUCCESS=Y";
//		String filepath="e:/private.key";
//		try{
//		//rsa.generateKeyPairIntoFile();
//		byte [] bytes=rsa.signDataByPriKey(string,filepath);
//            System.out.println(bytes.length);
//            System.out.println(rsa.checkSignedInfo(string,bytes,"e:/public.key"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}

        System.out.println("aaaa".startsWith(""));
	}

}
