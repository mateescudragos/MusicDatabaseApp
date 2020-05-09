package com.dmateescu.uipart;

import com.dmateescu.datamodel.Datasource;
import com.dmateescu.exceptions.DatabaseException;
import com.dmateescu.exceptions.QueryException;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;

import javax.sql.DataSource;

import static com.dmateescu.datamodel.Datasource.ORDER_BY_ASC;
import static com.dmateescu.datamodel.Datasource.ORDER_BY_DESC;
import static com.dmateescu.datamodel.Datasource.ORDER_BY_NONE;

public class Controller {
    @FXML
    private Button testButton;

    public void testButtonEvent(){
        Datasource dataSource = Datasource.getInstance();
        try {
            dataSource.open();
            System.out.println(dataSource.queryAlbums(ORDER_BY_ASC).size());
            dataSource.close();
        } catch (DatabaseException | QueryException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }


    }
}
