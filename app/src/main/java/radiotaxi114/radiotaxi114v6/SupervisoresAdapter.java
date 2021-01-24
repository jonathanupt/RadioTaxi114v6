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
public class SupervisoresAdapter extends BaseAdapter {

    private static ArrayList<SupervisoresResults> searchArrayList;

    private LayoutInflater mInflater;

    public SupervisoresAdapter(Context context, ArrayList<SupervisoresResults> results) {
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

            convertView = mInflater.inflate(R.layout.row_supervisores, null);

            holder = new ViewHolder();

            holder.txtConductorNombre = (TextView) convertView.findViewById(R.id.CmpSoporteContactoCelular);
            holder.txtConductorCelular = (TextView) convertView.findViewById(R.id.CmpConductorCelular);
            holder.txtConductorInspectorTurno = (TextView) convertView.findViewById(R.id.CmpConductorInspectorTurno);
            holder.imgTurno = (ImageView) convertView.findViewById(R.id.ImgSupervisoresTurno);

           //holder.txtConductorTelefono = (TextView) convertView.findViewById(R.id.CmpConductorTelefono);
           // holder.txtConductorEstadoDescripcion = (TextView) convertView.findViewById(R.id.CmpConductorEstadoDescripcion);

            holder.txtVehiculoUnidad = (TextView) convertView.findViewById(R.id.CmpSoporteContactoNombre);
           // holder.txtVehiculoPlaca = (TextView) convertView.findViewById(R.id.CmpVehiculoPlaca );
            holder.imgFoto = (ImageView) convertView.findViewById(R.id.ImgFoto);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtConductorNombre.setText(searchArrayList.get(position).getConductorNombre()+" "+searchArrayList.get(position).getConductorApellido());
        holder.txtConductorCelular.setText(searchArrayList.get(position).getConductorCelular());
        holder.txtConductorInspectorTurno.setText(searchArrayList.get(position).getConductorInspectorTurno());

        //holder.txtConductorTelefono.setText(searchArrayList.get(position).getConductorTelefono());
       // holder.txtConductorEstadoDescripcion.setText(searchArrayList.get(position).getConductorEstadoDescripcion());

        holder.txtVehiculoUnidad.setText(searchArrayList.get(position).getVehiculoUnidad());
        //holder.txtVehiculoPlaca.setText(searchArrayList.get(position).getVehiculoPlaca());

        if(searchArrayList.get(position).getConductorInspectorVigente().equals("1")){
            holder.imgFoto.setImageResource(R.mipmap.icon_agenda_supervisor_turno150);
        }else{
            holder.imgFoto.setImageResource(R.mipmap.icon_agenda_supervisor150);
        }

        if(searchArrayList.get(position).getConductorInspectorTurno().equals("DIA")){
            holder.imgTurno.setImageResource(R.mipmap.icon_dia50);

        }else  if(searchArrayList.get(position).getConductorInspectorTurno().equals("NOCHE")){
            holder.imgTurno.setImageResource(R.mipmap.icon_noche50);

        }else  if(searchArrayList.get(position).getConductorInspectorTurno().equals("TARDE")){
            holder.imgTurno.setImageResource(R.mipmap.icon_tarde50);

        }


        return convertView;
    }

    static class ViewHolder {

        TextView txtConductorNombre;
        TextView txtConductorCelular;
        TextView txtConductorInspectorTurno;
        //TextView txtConductorTelefono;
        //TextView txtConductorEstadoDescripcion;

        TextView txtVehiculoUnidad;
        //TextView txtVehiculoPlaca;
        ImageView imgFoto;
        ImageView imgTurno;

    }


}
