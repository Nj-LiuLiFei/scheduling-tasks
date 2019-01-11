package cn.liulifei.schedulingtasks.vo;

import cn.liulifei.schedulingtasks.dto.PagingQueryBase;
import com.alibaba.fastjson.JSONArray;

public class PagingResultVo {
    private JSONArray data;
    private long recordsTotal;
    private long recordsFiltered;
    private int draw;
    private String message;
    private String error;

    public PagingResultVo(PagingQueryBase pagingQueryBase,JSONArray jsonArray,long recordsTotal){
        this.setDraw(pagingQueryBase.getDraw());
        this.setRecordsTotal(recordsTotal);
        this.setData(jsonArray);
        this.setRecordsFiltered(jsonArray.size());
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public JSONArray getData() {
        return data;
    }

    public void setData(JSONArray data) {
        this.data = data;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
