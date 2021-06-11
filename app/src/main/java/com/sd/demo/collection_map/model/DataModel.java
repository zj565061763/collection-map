package com.sd.demo.collection_map.model;

import java.util.ArrayList;
import java.util.List;

public class DataModel {
    public final Object mObject;

    public DataModel(Object object) {
        mObject = object;
    }

    public static List<DataModel> get(int count) {
        final List<DataModel> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(new DataModel(String.valueOf(i)));
        }
        return list;
    }

    @Override
    public int hashCode() {
        return mObject.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        final DataModel other = (DataModel) obj;
        return mObject.equals(other.mObject);
    }
}
