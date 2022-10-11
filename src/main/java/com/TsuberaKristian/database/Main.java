package com.TsuberaKristian.database;




public class Main {


    public static void main(String[] args) {
        String exercise = "25+81*9+(45-21)/2";
        String exercise2 = "6.5 + (6.5 + 6.5)";

        Database database = new Database();
        Calculated calculator = new Calculated();

        database.insertInBase(exercise,calculator);
        database.info();
        database.updateSql(3,exercise2,calculator);





    }
}
