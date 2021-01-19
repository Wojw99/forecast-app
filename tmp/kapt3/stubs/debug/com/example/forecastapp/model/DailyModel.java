package com.example.forecastapp.model;

import java.lang.System;

@kotlin.Metadata(mv = {1, 1, 16}, bv = {1, 0, 3}, k = 1, d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\'\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B{\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0003\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\r\u0012\u0006\u0010\u000f\u001a\u00020\r\u0012\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u0012\u0006\u0010\u0013\u001a\u00020\r\u0012\u0006\u0010\u0014\u001a\u00020\r\u0012\u0006\u0010\u0015\u001a\u00020\r\u00a2\u0006\u0002\u0010\u0016J\t\u0010*\u001a\u00020\u0003H\u00c6\u0003J\t\u0010+\u001a\u00020\rH\u00c6\u0003J\u000f\u0010,\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H\u00c6\u0003J\t\u0010-\u001a\u00020\rH\u00c6\u0003J\t\u0010.\u001a\u00020\rH\u00c6\u0003J\t\u0010/\u001a\u00020\rH\u00c6\u0003J\t\u00100\u001a\u00020\u0003H\u00c6\u0003J\t\u00101\u001a\u00020\u0003H\u00c6\u0003J\t\u00102\u001a\u00020\u0007H\u00c6\u0003J\t\u00103\u001a\u00020\tH\u00c6\u0003J\t\u00104\u001a\u00020\u0003H\u00c6\u0003J\t\u00105\u001a\u00020\u0003H\u00c6\u0003J\t\u00106\u001a\u00020\rH\u00c6\u0003J\t\u00107\u001a\u00020\rH\u00c6\u0003J\u009b\u0001\u00108\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\r2\b\b\u0002\u0010\u000e\u001a\u00020\r2\b\b\u0002\u0010\u000f\u001a\u00020\r2\u000e\b\u0002\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\b\b\u0002\u0010\u0013\u001a\u00020\r2\b\b\u0002\u0010\u0014\u001a\u00020\r2\b\b\u0002\u0010\u0015\u001a\u00020\rH\u00c6\u0001J\u0013\u00109\u001a\u00020:2\b\u0010;\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010<\u001a\u00020\u0003H\u00d6\u0001J\t\u0010=\u001a\u00020>H\u00d6\u0001R\u0011\u0010\u0013\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0011\u0010\b\u001a\u00020\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0011\u0010\u000b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001bR\u0011\u0010\u0014\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0018R\u0011\u0010\n\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001bR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u001bR\u0011\u0010\u0005\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u001bR\u0011\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0011\u0010\u0015\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0018R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011\u00a2\u0006\b\n\u0000\u001a\u0004\b&\u0010\'R\u0011\u0010\u000f\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b(\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\r\u00a2\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0018\u00a8\u0006?"}, d2 = {"Lcom/example/forecastapp/model/DailyModel;", "", "dt", "", "sunrise", "sunset", "temp", "Lcom/example/forecastapp/model/TempModel;", "feels_like", "Lcom/example/forecastapp/model/FeelsLikeModel;", "pressure", "humidity", "dew_point", "", "wind_speed", "wind_deg", "weather", "", "Lcom/example/forecastapp/model/WeatherModel;", "clouds", "pop", "uvi", "(IIILcom/example/forecastapp/model/TempModel;Lcom/example/forecastapp/model/FeelsLikeModel;IIDDDLjava/util/List;DDD)V", "getClouds", "()D", "getDew_point", "getDt", "()I", "getFeels_like", "()Lcom/example/forecastapp/model/FeelsLikeModel;", "getHumidity", "getPop", "getPressure", "getSunrise", "getSunset", "getTemp", "()Lcom/example/forecastapp/model/TempModel;", "getUvi", "getWeather", "()Ljava/util/List;", "getWind_deg", "getWind_speed", "component1", "component10", "component11", "component12", "component13", "component14", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "toString", "", "app_debug"})
public final class DailyModel {
    private final int dt = 0;
    private final int sunrise = 0;
    private final int sunset = 0;
    @org.jetbrains.annotations.NotNull()
    private final com.example.forecastapp.model.TempModel temp = null;
    @org.jetbrains.annotations.NotNull()
    private final com.example.forecastapp.model.FeelsLikeModel feels_like = null;
    private final int pressure = 0;
    private final int humidity = 0;
    private final double dew_point = 0.0;
    private final double wind_speed = 0.0;
    private final double wind_deg = 0.0;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.example.forecastapp.model.WeatherModel> weather = null;
    private final double clouds = 0.0;
    private final double pop = 0.0;
    private final double uvi = 0.0;
    
    public final int getDt() {
        return 0;
    }
    
    public final int getSunrise() {
        return 0;
    }
    
    public final int getSunset() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.forecastapp.model.TempModel getTemp() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.forecastapp.model.FeelsLikeModel getFeels_like() {
        return null;
    }
    
    public final int getPressure() {
        return 0;
    }
    
    public final int getHumidity() {
        return 0;
    }
    
    public final double getDew_point() {
        return 0.0;
    }
    
    public final double getWind_speed() {
        return 0.0;
    }
    
    public final double getWind_deg() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.forecastapp.model.WeatherModel> getWeather() {
        return null;
    }
    
    public final double getClouds() {
        return 0.0;
    }
    
    public final double getPop() {
        return 0.0;
    }
    
    public final double getUvi() {
        return 0.0;
    }
    
    public DailyModel(int dt, int sunrise, int sunset, @org.jetbrains.annotations.NotNull()
    com.example.forecastapp.model.TempModel temp, @org.jetbrains.annotations.NotNull()
    com.example.forecastapp.model.FeelsLikeModel feels_like, int pressure, int humidity, double dew_point, double wind_speed, double wind_deg, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.forecastapp.model.WeatherModel> weather, double clouds, double pop, double uvi) {
        super();
    }
    
    public final int component1() {
        return 0;
    }
    
    public final int component2() {
        return 0;
    }
    
    public final int component3() {
        return 0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.forecastapp.model.TempModel component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.forecastapp.model.FeelsLikeModel component5() {
        return null;
    }
    
    public final int component6() {
        return 0;
    }
    
    public final int component7() {
        return 0;
    }
    
    public final double component8() {
        return 0.0;
    }
    
    public final double component9() {
        return 0.0;
    }
    
    public final double component10() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.example.forecastapp.model.WeatherModel> component11() {
        return null;
    }
    
    public final double component12() {
        return 0.0;
    }
    
    public final double component13() {
        return 0.0;
    }
    
    public final double component14() {
        return 0.0;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.example.forecastapp.model.DailyModel copy(int dt, int sunrise, int sunset, @org.jetbrains.annotations.NotNull()
    com.example.forecastapp.model.TempModel temp, @org.jetbrains.annotations.NotNull()
    com.example.forecastapp.model.FeelsLikeModel feels_like, int pressure, int humidity, double dew_point, double wind_speed, double wind_deg, @org.jetbrains.annotations.NotNull()
    java.util.List<com.example.forecastapp.model.WeatherModel> weather, double clouds, double pop, double uvi) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public java.lang.String toString() {
        return null;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object p0) {
        return false;
    }
}