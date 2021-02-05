package com.example.forecastapp.model.data;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.forecastapp.model.HistoricalDailyModel;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class HistoricalDailyDao_Impl implements HistoricalDailyDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<HistoricalDailyModel> __insertionAdapterOfHistoricalDailyModel;

  private final EntityDeletionOrUpdateAdapter<HistoricalDailyModel> __deletionAdapterOfHistoricalDailyModel;

  private final SharedSQLiteStatement __preparedStmtOfDeleteall;

  public HistoricalDailyDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfHistoricalDailyModel = new EntityInsertionAdapter<HistoricalDailyModel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `tab_historicalweather` (`id`,`dt`,`sunrise`,`sunset`,`temp`,`feels_like`,`pressure`,`humidity`,`dew_point`,`wind_speed`,`wind_deg`,`weather`,`clouds`,`pop`,`uvi`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HistoricalDailyModel value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getDt());
        stmt.bindLong(3, value.getSunrise());
        stmt.bindLong(4, value.getSunset());
        stmt.bindDouble(5, value.getTemp());
        stmt.bindDouble(6, value.getFeels_like());
        stmt.bindLong(7, value.getPressure());
        stmt.bindLong(8, value.getHumidity());
        stmt.bindDouble(9, value.getDew_point());
        stmt.bindDouble(10, value.getWind_speed());
        stmt.bindDouble(11, value.getWind_deg());
        if (value.getWeather() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getWeather());
        }
        stmt.bindDouble(13, value.getClouds());
        stmt.bindDouble(14, value.getPop());
        stmt.bindDouble(15, value.getUvi());
      }
    };
    this.__deletionAdapterOfHistoricalDailyModel = new EntityDeletionOrUpdateAdapter<HistoricalDailyModel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `tab_historicalweather` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, HistoricalDailyModel value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__preparedStmtOfDeleteall = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM tab_historicalweather";
        return _query;
      }
    };
  }

  @Override
  public Object add(final HistoricalDailyModel historicalDailyModel,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfHistoricalDailyModel.insert(historicalDailyModel);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object delete(final HistoricalDailyModel historicalDailyModel,
      final Continuation<? super Unit> p1) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfHistoricalDailyModel.handle(historicalDailyModel);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, p1);
  }

  @Override
  public Object deleteall(final Continuation<? super Unit> p0) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteall.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteall.release(_stmt);
        }
      }
    }, p0);
  }

  @Override
  public LiveData<List<HistoricalDailyModel>> historicaldailyall() {
    final String _sql = "SELECT * FROM tab_historicalweather ORDER BY id ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"tab_historicalweather"}, false, new Callable<List<HistoricalDailyModel>>() {
      @Override
      public List<HistoricalDailyModel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfDt = CursorUtil.getColumnIndexOrThrow(_cursor, "dt");
          final int _cursorIndexOfSunrise = CursorUtil.getColumnIndexOrThrow(_cursor, "sunrise");
          final int _cursorIndexOfSunset = CursorUtil.getColumnIndexOrThrow(_cursor, "sunset");
          final int _cursorIndexOfTemp = CursorUtil.getColumnIndexOrThrow(_cursor, "temp");
          final int _cursorIndexOfFeelsLike = CursorUtil.getColumnIndexOrThrow(_cursor, "feels_like");
          final int _cursorIndexOfPressure = CursorUtil.getColumnIndexOrThrow(_cursor, "pressure");
          final int _cursorIndexOfHumidity = CursorUtil.getColumnIndexOrThrow(_cursor, "humidity");
          final int _cursorIndexOfDewPoint = CursorUtil.getColumnIndexOrThrow(_cursor, "dew_point");
          final int _cursorIndexOfWindSpeed = CursorUtil.getColumnIndexOrThrow(_cursor, "wind_speed");
          final int _cursorIndexOfWindDeg = CursorUtil.getColumnIndexOrThrow(_cursor, "wind_deg");
          final int _cursorIndexOfWeather = CursorUtil.getColumnIndexOrThrow(_cursor, "weather");
          final int _cursorIndexOfClouds = CursorUtil.getColumnIndexOrThrow(_cursor, "clouds");
          final int _cursorIndexOfPop = CursorUtil.getColumnIndexOrThrow(_cursor, "pop");
          final int _cursorIndexOfUvi = CursorUtil.getColumnIndexOrThrow(_cursor, "uvi");
          final List<HistoricalDailyModel> _result = new ArrayList<HistoricalDailyModel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final HistoricalDailyModel _item;
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            final int _tmpDt;
            _tmpDt = _cursor.getInt(_cursorIndexOfDt);
            final int _tmpSunrise;
            _tmpSunrise = _cursor.getInt(_cursorIndexOfSunrise);
            final int _tmpSunset;
            _tmpSunset = _cursor.getInt(_cursorIndexOfSunset);
            final double _tmpTemp;
            _tmpTemp = _cursor.getDouble(_cursorIndexOfTemp);
            final double _tmpFeels_like;
            _tmpFeels_like = _cursor.getDouble(_cursorIndexOfFeelsLike);
            final int _tmpPressure;
            _tmpPressure = _cursor.getInt(_cursorIndexOfPressure);
            final int _tmpHumidity;
            _tmpHumidity = _cursor.getInt(_cursorIndexOfHumidity);
            final double _tmpDew_point;
            _tmpDew_point = _cursor.getDouble(_cursorIndexOfDewPoint);
            final double _tmpWind_speed;
            _tmpWind_speed = _cursor.getDouble(_cursorIndexOfWindSpeed);
            final double _tmpWind_deg;
            _tmpWind_deg = _cursor.getDouble(_cursorIndexOfWindDeg);
            final String _tmpWeather;
            _tmpWeather = _cursor.getString(_cursorIndexOfWeather);
            final double _tmpClouds;
            _tmpClouds = _cursor.getDouble(_cursorIndexOfClouds);
            final double _tmpPop;
            _tmpPop = _cursor.getDouble(_cursorIndexOfPop);
            final double _tmpUvi;
            _tmpUvi = _cursor.getDouble(_cursorIndexOfUvi);
            _item = new HistoricalDailyModel(_tmpId,_tmpDt,_tmpSunrise,_tmpSunset,_tmpTemp,_tmpFeels_like,_tmpPressure,_tmpHumidity,_tmpDew_point,_tmpWind_speed,_tmpWind_deg,_tmpWeather,_tmpClouds,_tmpPop,_tmpUvi);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }
}
