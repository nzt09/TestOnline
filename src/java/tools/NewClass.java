/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tools;

/**
 *
 * @author Administrator
 */
public class NewClass {
    public static void main(String[] args) {
        String aa = "1###############";
        String[] ss = aa.split("#");
        for(int i = 0;i<ss.length;i++){
            System.out.println(ss[i]);
        }
    }
}
