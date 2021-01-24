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
 * Created by USUARIO on 07/05/2015.
 */
public class RecepcionArchivosAdapter extends BaseAdapter {


    private static ArrayList<RecepcionArchivosResults> searchArrayList;

    private LayoutInflater mInflater;

    public RecepcionArchivosAdapter(Context context, ArrayList<RecepcionArchivosResults> results) {
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
            convertView = mInflater.inflate(R.layout.row_archivos, null);

            holder = new ViewHolder();
            holder.txtEnvioArchivoNombre = (TextView) convertView.findViewById(R.id.CmpSoporteContactoCelular);
            holder.txtEnvioArchivoTiempoCreacion = (TextView) convertView.findViewById(R.id.CmpEnvioArchivoTiempoCreacion);           
            holder.imgEnvioArchivoTipo = (ImageView) convertView.findViewById(R.id.ImgFoto);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtEnvioArchivoNombre.setText(searchArrayList.get(position).getEnvioArchivoNombre());
        holder.txtEnvioArchivoTiempoCreacion.setText(searchArrayList.get(position).getEnvioArchivoTiempoCreacion());

        if(searchArrayList.get(position).getEnvioArchivoTipo().equals("PDF")) {

            holder.imgEnvioArchivoTipo.setImageResource(R.mipmap.icon_pdf50);

        }else if(searchArrayList.get(position).getEnvioArchivoTipo().equals("IMAGEN")) {

            holder.imgEnvioArchivoTipo.setImageResource(R.mipmap.icon_imagen50);

        }else{

            holder.imgEnvioArchivoTipo.setImageResource(R.mipmap.icon_desconocido50);

        }



        return convertView;
    }

    static class ViewHolder {

        TextView txtEnvioArchivoNombre;
        TextView txtEnvioArchivoTiempoCreacion;
        ImageView imgEnvioArchivoTipo;

    }


}
