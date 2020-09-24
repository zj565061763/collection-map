package com.sd.collection_map;

public class DataModel
{
    public final Object mObject;

    public DataModel(Object object)
    {
        mObject = object;
    }

    @Override
    public int hashCode()
    {
        return mObject.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != getClass()) return false;
        final DataModel other = (DataModel) obj;
        return mObject.equals(other.mObject);
    }
}
