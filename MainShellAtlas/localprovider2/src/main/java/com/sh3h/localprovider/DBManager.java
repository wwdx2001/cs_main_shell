package com.sh3h.localprovider;



public class DBManager {
    private static IDataProvider dataProvider = null;

    public static IDataProvider getInstance() {
        if (dataProvider == null) {
            synchronized (IDataProvider.class){
                if(dataProvider == null){
                    dataProvider = new DataProviderImpl();
                }
            }
        }
        return dataProvider;
    }
}
