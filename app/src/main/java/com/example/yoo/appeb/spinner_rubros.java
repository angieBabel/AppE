package com.example.yoo.appeb;

/**
 * Created by jordigarcia on 9/25/16.
 */

public class spinner_rubros {

        private String idRubro;
        private String nombreRubro;
        //private String idProducto;

        public spinner_rubros(String idRubro, String nombreRubro) {
            this.idRubro = idRubro;
            this.nombreRubro = nombreRubro;
        }

        public String getNombre() {
            return nombreRubro;
        }

        public void setNombre(String nombreRubro) {
            this.nombreRubro = nombreRubro;
        }

        public String getidRubro() {
            return idRubro;
        }

        public void setidRubro(String idRubro) {
            this.idRubro = idRubro;
        }


}
