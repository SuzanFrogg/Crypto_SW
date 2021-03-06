/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package challenges;

import algorithmes.chiffrement.AlgorithmeRSA;
import donnees.MotBinaire;
import donnees.NombreBinaire;
import donnees.cles.CleBinaire;
import donnees.cles.Cles;
import donnees.messages.Message;
import donnees.messages.MessageBinaire;
import exceptions.ExceptionConversionImpossible;
import exceptions.ExceptionCryptographie;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe permettant la validation du challenge n°21 : Chiffrer
 * @author Albane
 */
public class Chiffrer extends Challenge{

    /**
     * Pour valider le challenge Chiffrer, il faut chiffrer le message reçu
     * à l'aide des clés
     * @return
     * @throws IOException 
     */
    @Override
    public String communicate() throws IOException {
        String res = "ALED";
        
        //On récupère la série de triplets envoyé par le serveur
        NombreBinaire mot1 = new NombreBinaire(getMsgReceive());
        MotBinaire M = new MotBinaire(mot1.asBitSet(), 252);
        setMsgReceive(getClient().receiveMessage());
        NombreBinaire n1 = new NombreBinaire(getMsgReceive());
        setMsgReceive(getClient().receiveMessage());
        NombreBinaire n2 = new NombreBinaire(getMsgReceive());
        
        //On initialise les clés N et e
        CleBinaire N = new CleBinaire(new MotBinaire(n1.toString()));
        CleBinaire e = new CleBinaire(new MotBinaire(n2.toString()));
        
        //On les ajoute à une liste de clés (liste de clés publiques)
        Cles cles = new Cles();
        cles.addCle("cleRSA_N", N);
        cles.addCle("cleRSA_e", e);
        
        //On créé un algorithmeRSA
        AlgorithmeRSA a = new AlgorithmeRSA();
        
        //On créé le message à partir du MotBinaire
        MessageBinaire msg = new MessageBinaire(M);
        
        //On essaie de chiffrer
        try {
            res = a.chiffrer(msg, cles, cles).asMotBinaire().toString();
        } catch (ExceptionConversionImpossible ex) {
            Logger.getLogger(ChiffrerUnMorceau.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExceptionCryptographie ex) {
            Logger.getLogger(Chiffrer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //On retourne le résultat
        return res;
    }
    
}
