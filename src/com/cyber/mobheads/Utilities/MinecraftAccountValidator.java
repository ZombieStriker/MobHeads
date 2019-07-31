/*    */ package com.cyber.mobheads.Utilities;
/*    */ 
/*    */ import com.google.gson.JsonElement;
/*    */ import com.google.gson.JsonObject;
/*    */ import com.google.gson.JsonParser;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.InputStreamReader;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MinecraftAccountValidator
/*    */ {
/*    */   public static boolean isValidPlayer(String playername) {
/*    */     try {
/* 26 */       String urlS = "https://api.mojang.com/users/profiles/minecraft/" + playername;
/*    */       
/* 28 */       URL url = new URL(urlS);
/* 29 */       HttpURLConnection request = (HttpURLConnection)url.openConnection();
/* 30 */       request.connect();
/*    */       
/* 32 */       JsonParser jp = new JsonParser();
/* 33 */       JsonElement root = jp.parse(new InputStreamReader((InputStream)request.getContent()));
/* 34 */       JsonObject rootobj = root.getAsJsonObject();
/* 35 */       return true;
/*    */     }
/* 37 */     catch (IOException ex) {
/* 38 */       return false;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              F:\Minecraft Servers\SpigotLobby\plugins\MobHeads 2.4.jar!\com\cyber\mobheads\Utilities\MinecraftAccountValidator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.0.7
 */