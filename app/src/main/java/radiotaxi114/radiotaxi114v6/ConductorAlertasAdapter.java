package radiotaxi114.radiotaxi114v6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by USUARIO on 02/07/2015.
 */
public class ConductorAlertasAdapter extends BaseAdapter {

    private static ArrayList<ConductorAlertasResults> searchArrayList;

    private LayoutInflater mInflater;

    public ConductorAlertasAdapter(Context context, ArrayList<ConductorAlertasResults> results) {
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

            convertView = mInflater.inflate(R.layout.row_conductor_alertas, null);

            holder = new ViewHolder();

            holder.txtTiempoCreacion = (TextView) convertView.findViewById(R.id.CmpConductorAlertaTiempoCreacion);
            holder.txtVehiculoUnidad = (TextView) convertView.findViewById(R.id.CmpSoporteContactoNombre);
            holder.txtConductorNombre = (TextView) convertView.findViewById(R.id.CmpSoporteContactoCelular);
            holder.imgAlertaTipo = (ImageView) convertView.findViewById(R.id.ImgFoto);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtTiempoCreacion.setText(searchArrayList.get(position).getConductorAlertaTiempoCreacion());
        holder.txtVehiculoUnidad.setText(searchArrayList.get(position).getVehiculoUnidad());
        holder.txtConductorNombre.setText(searchArrayList.get(position).getConductorNombre());

        if(searchArrayList.get(position).getConductorAlertaTipo().equals("1")){
            holder.imgAlertaTipo.setImageResource(R.mipmap.icon_robo35);
        }else if(searchArrayList.get(position).getConductorAlertaTipo().equals("2")){
            holder.imgAlertaTipo.setImageResource(R.mipmap.icon_operativo35);
        }else if(searchArrayList.get(position).getConductorAlertaTipo().equals("3")){
            holder.imgAlertaTipo.setImageResource(R.mipmap.icon_choque35);
        }

        return convertView;
    }

    static class ViewHolder {

        TextView txtTiempoCreacion;
        TextView txtVehiculoUnidad;
        TextView txtConductorNombre;

        ImageView imgAlertaTipo;
    }


}
