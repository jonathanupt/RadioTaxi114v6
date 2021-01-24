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
public class HistorialAdapter extends BaseAdapter {

    private static ArrayList<HistorialResults> searchArrayList;

    private LayoutInflater mInflater;

    public HistorialAdapter(Context context, ArrayList<HistorialResults> results) {
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

            convertView = mInflater.inflate(R.layout.row_historial, null);

            holder = new ViewHolder();

            holder.txtDireccion = (TextView) convertView.findViewById(R.id.CmpSoporteContactoCelular);
            holder.txtNumero = (TextView) convertView.findViewById(R.id.CmpNumero);
            holder.txtReferencia = (TextView) convertView.findViewById(R.id.CmpReferencia);
            holder.txtFecha = (TextView) convertView.findViewById(R.id.CmpSoporteContactoNombre);
            holder.txtHora = (TextView) convertView.findViewById(R.id.CmpHora);

            ///holder.txtVehiculoUnidad = (TextView) convertView.findViewById(R.id.CmpVehiculoUnidad);
            //holder.txtVehiculoPlaca = (TextView) convertView.findViewById(R.id.CmpVehiculoPlaca);
            //holder.txtVehiculoColor = (TextView) convertView.findViewById(R.id.CmpVehiculoColor);
            //holder.txtVehiculoConductor = (TextView) convertView.findViewById(R.id.CmpCliente);
            holder.txtClienteNombre = (TextView) convertView.findViewById(R.id.CmpClienteNombre);
            holder.txtPedidoCalificacion = (TextView) convertView.findViewById(R.id.CmpPedidoCalificacion);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtDireccion.setText(searchArrayList.get(position).getPedidoDireccion());
        holder.txtNumero.setText(searchArrayList.get(position).getPedidoNumero());
        holder.txtReferencia.setText(searchArrayList.get(position).getPedidoReferencia());
        holder.txtFecha.setText(searchArrayList.get(position).getPedidoFecha());
        holder.txtHora.setText(searchArrayList.get(position).getPedidoHora());

        //holder.txtVehiculoUnidad.setText(searchArrayList.get(position).getVehiculoUnidad());
        //holder.txtVehiculoPlaca.setText(searchArrayList.get(position).getVehiculoPlaca());
        //holder.txtVehiculoColor.setText(searchArrayList.get(position).getVehiculoColor());
        //holder.txtVehiculoConductor.setText(searchArrayList.get(position).getVehiculoConductor());
        holder.txtClienteNombre.setText(searchArrayList.get(position).getClienteNombre());
        holder.txtPedidoCalificacion.setText(searchArrayList.get(position).getPedidoCalificacion());

        return convertView;
    }

    static class ViewHolder {

        TextView txtDireccion;
        TextView txtNumero;
        TextView txtReferencia;
        TextView txtFecha;
        TextView txtHora;
        TextView txtVehiculoUnidad;
        TextView txtVehiculoPlaca;
        TextView txtVehiculoColor;
        TextView txtVehiculoConductor;
        TextView txtClienteNombre;
        TextView txtPedidoCalificacion;

    }


}
