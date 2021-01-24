package radiotaxi114.radiotaxi114v6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by USUARIO on 02/07/2015.
 */
public class ConductoresAdapter extends BaseAdapter {

    private static ArrayList<ConductoresResults> searchArrayList;

    private LayoutInflater mInflater;

    public ConductoresAdapter(Context context, ArrayList<ConductoresResults> results) {
        searchArrayList = results;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return searchArrayList.size();
    }

    public Object getItem(int position) {
        return searchArrayList.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = mInflater.inflate(R.layout.row_conductores, null);

            holder = new ViewHolder();

            holder.txtConductorNombre = (TextView) convertView.findViewById(R.id.CmpSoporteContactoCelular);
            holder.txtConductorCelular = (TextView) convertView.findViewById(R.id.CmpConductorCelular);
            holder.txtConductorTelefono = (TextView) convertView.findViewById(R.id.CmpConductorTelefono);
            holder.txtConductorEstadoDescripcion = (TextView) convertView.findViewById(R.id.CmpConductorEstadoDescripcion);
           // holder.txtConductorInspectorTurno = (TextView) convertView.findViewById(R.id.CmpConductorInspectorTurno);

            holder.txtVehiculoUnidad = (TextView) convertView.findViewById(R.id.CmpSoporteContactoNombre);
            holder.txtVehiculoPlaca = (TextView) convertView.findViewById(R.id.CmpVehiculoPlaca );

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtConductorNombre.setText(searchArrayList.get(position).getConductorNombre()+" "+searchArrayList.get(position).getConductorApellido());
        holder.txtConductorCelular.setText(searchArrayList.get(position).getConductorCelular());
        holder.txtConductorTelefono.setText(searchArrayList.get(position).getConductorTelefono());
        holder.txtConductorEstadoDescripcion.setText(searchArrayList.get(position).getConductorEstadoDescripcion());
        //holder.txtConductorInspectorTurno.setText(searchArrayList.get(position).getConductorInspectorTurno());

        holder.txtVehiculoUnidad.setText(searchArrayList.get(position).getVehiculoUnidad());
        holder.txtVehiculoPlaca.setText(searchArrayList.get(position).getVehiculoPlaca());

        return convertView;
    }

    static class ViewHolder {

        TextView txtConductorNombre;
        TextView txtConductorCelular;
        TextView txtConductorTelefono;
        TextView txtConductorEstadoDescripcion;
        //TextView txtConductorInspectorTurno

        TextView txtVehiculoUnidad;
        TextView txtVehiculoPlaca;


    }


}
