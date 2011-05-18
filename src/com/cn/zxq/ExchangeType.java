package com.cn.zxq;

public enum ExchangeType {

	AUDJPY("audjpy", Integer.valueOf("1")),
   
	CADJPY("cadjpy", Integer.valueOf("2")),
   
	CHFJPY("chfjpy", Integer.valueOf("3")),
    
	GBPJPY("gbpjpy", Integer.valueOf("4")),
    
	GBPUSD("gbpusd", Integer.valueOf("5")),
    
	HKDJPY("hkdjpy", Integer.valueOf("6")),
    
	USDJPY("usdjpy", Integer.valueOf("7")),
	
	ZARJPY("zarjpy", Integer.valueOf("8")),
    ;
    
    private String localeName;
    private Integer aliasName;

    private ExchangeType(String localeName, Integer aliasName) {
        this.localeName = localeName;
        this.aliasName = aliasName;
    }

    public String localeName() {
        return localeName;
    }
    public Integer aliasName() {
        return aliasName;
    }
}
