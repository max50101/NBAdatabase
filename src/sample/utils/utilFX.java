package sample.utils;

import javafx.scene.control.TableView;
import javafx.scene.text.Text;

public class utilFX {
    public static void autoResizeColumns( TableView<?> table )
    {

        table.setColumnResizePolicy( TableView.UNCONSTRAINED_RESIZE_POLICY);
        table.getColumns().stream().forEach( (column) ->
        {

            Text t = new Text( column.getText() );
            double max = t.getLayoutBounds().getWidth();
            for ( int i = 0; i < table.getItems().size(); i++ )
            {

                if ( column.getCellData( i ) != null )
                {
                    t = new Text( column.getCellData( i ).toString() );
                    double calcwidth = t.getLayoutBounds().getWidth();

                    if ( calcwidth > max )
                    {
                        max = calcwidth;
                    }
                }
            }

            column.setPrefWidth( max + 10.0d );
        } );
    }
}
