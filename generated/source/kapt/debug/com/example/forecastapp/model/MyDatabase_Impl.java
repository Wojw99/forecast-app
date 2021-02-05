package com.example.forecastapp.model;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.forecastapp.model.data.HistoricalDailyDao;
import com.example.forecastapp.model.data.HistoricalDailyDao_Impl;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class MyDatabase_Impl extends MyDatabase {
  private volatile HistoricalDailyDao _historicalDailyDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(4) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `tab_historicalweather` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `dt` INTEGER NOT NULL, `sunrise` INTEGER NOT NULL, `sunset` INTEGER NOT NULL, `temp` REAL NOT NULL, `feels_like` REAL NOT NULL, `pressure` INTEGER NOT NULL, `humidity` INTEGER NOT NULL, `dew_point` REAL NOT NULL, `wind_speed` REAL NOT NULL, `wind_deg` REAL NOT NULL, `weather` TEXT NOT NULL, `clouds` REAL NOT NULL, `pop` REAL NOT NULL, `uvi` REAL NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '9e1415765455bcbbe2aacf318fe781a2')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `tab_historicalweather`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsTabHistoricalweather = new HashMap<String, TableInfo.Column>(15);
        _columnsTabHistoricalweather.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("dt", new TableInfo.Column("dt", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("sunrise", new TableInfo.Column("sunrise", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("sunset", new TableInfo.Column("sunset", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("temp", new TableInfo.Column("temp", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("feels_like", new TableInfo.Column("feels_like", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("pressure", new TableInfo.Column("pressure", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("humidity", new TableInfo.Column("humidity", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("dew_point", new TableInfo.Column("dew_point", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("wind_speed", new TableInfo.Column("wind_speed", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("wind_deg", new TableInfo.Column("wind_deg", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("weather", new TableInfo.Column("weather", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("clouds", new TableInfo.Column("clouds", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("pop", new TableInfo.Column("pop", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTabHistoricalweather.put("uvi", new TableInfo.Column("uvi", "REAL", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTabHistoricalweather = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTabHistoricalweather = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTabHistoricalweather = new TableInfo("tab_historicalweather", _columnsTabHistoricalweather, _foreignKeysTabHistoricalweather, _indicesTabHistoricalweather);
        final TableInfo _existingTabHistoricalweather = TableInfo.read(_db, "tab_historicalweather");
        if (! _infoTabHistoricalweather.equals(_existingTabHistoricalweather)) {
          return new RoomOpenHelper.ValidationResult(false, "tab_historicalweather(com.example.forecastapp.model.HistoricalDailyModel).\n"
                  + " Expected:\n" + _infoTabHistoricalweather + "\n"
                  + " Found:\n" + _existingTabHistoricalweather);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "9e1415765455bcbbe2aacf318fe781a2", "888a840c87fccd60e29809f9e8c510cf");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "tab_historicalweather");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `tab_historicalweather`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public HistoricalDailyDao historicalDailyDao() {
    if (_historicalDailyDao != null) {
      return _historicalDailyDao;
    } else {
      synchronized(this) {
        if(_historicalDailyDao == null) {
          _historicalDailyDao = new HistoricalDailyDao_Impl(this);
        }
        return _historicalDailyDao;
      }
    }
  }
}
