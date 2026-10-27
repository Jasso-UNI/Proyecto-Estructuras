/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package interfazproyecto;

public class InterfazProyecto {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            // 1. Crear la instancia de tu ventana principal
            PrincipalInterface inicio = new PrincipalInterface();
            
            // 2. Centrar la ventana en la pantalla
            inicio.setLocationRelativeTo(null);
            
            // 3. Hacerla visible
            inicio.setVisible(true);
        });
    }
}