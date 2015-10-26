
package com.dperez.inalerlab.operario.credenciales;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import javax.ejb.Stateless;
import javax.inject.Named;

/**
 *
 * @author dperez
 */
@Named
@Stateless
public class Seguridad implements Serializable{
   //   constructores
    
    public Seguridad(){
    }

    /**
     * Devuelve la combinacion passord + salt
     * Se utiliza para crear un hash del password con una cadena aleatoria (salt) agregada para aumentar seguridad.
     * Esta cadena se debe utilizar para recuperar el password.
     * @param Password
     * @return Array[0]: salt | Array[1]: passwordSeguro
     */
    public String[] getPasswordSeguro(String Password){
        String[] pass = new String[2];
        try{
            pass[0] = getSalt();
            pass[1] = getSecurePassword(Password, pass[0]);
        }catch(NoSuchProviderException | NoSuchAlgorithmException ex){}
        return pass;
    }
    
    /**
     * Devuelve el password correspondiente al salt.
     * @param Password
     * @param Salt
     * @return 
     */
    public String getPasswordSeguro(String Password, String Salt){
        return getSecurePassword(Password, Salt);
    }
    
    private static String getSecurePassword(String passwordToHash, String salt) {
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            //Add password bytes to digest
            md.update(salt.getBytes());
            //Get the hash's bytes
            byte[] bytes = md.digest(passwordToHash.getBytes());
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
     
    //Add salt
    private static String getSalt() throws NoSuchAlgorithmException, NoSuchProviderException{
        //Always use a SecureRandom generator
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        //Create array for salt
        byte[] salt = new byte[16];
        //Get a random salt
        sr.nextBytes(salt);
        //return salt
        return salt.toString();
    }
    
}
