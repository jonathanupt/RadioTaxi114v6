package radiotaxi114.radiotaxi114v6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Jonathan on 29/03/2017.
 */
public class SoporteContactoAdapter extends BaseAdapter {

    private static ArrayList<SoporteContactoResults> searchArrayList;

    private LayoutInflater mInflater;

    public SoporteContactoAdapter(Context context, ArrayList<SoporteContactoResults> results) {
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

            convertView = mInflater.inflate(R.layout.row_soporte_contacto, null);

            holder = new ViewHolder();

            holder.txtSoporteContactoNombre = (TextView) convertView.findViewById(R.id.CmpSoporteContactoNombre);
            //holder.txtSoporteContactoTelefono = (TextView) convertView.findViewById(R.id.CmpSoporteContactoTelefonoCelular);
            holder.txtSoporteContactoCelular = (TextView) convertView.findViewById(R.id.CmpSoporteContactoCelular);
            //holder.txtSoporteContactoTelefonoCelular = (TextView) convertView.findViewById(R.id.CmpSoporteContactoTelefonoCelular);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtSoporteContactoNombre.setText(searchArrayList.get(position).getSoporteContactoNombre());
        //holder.txtSoporteContactoTelefono.setText(searchArrayList.get(position).getSoporteContactoTelefono());
        holder.txtSoporteContactoCelular.setText(searchArrayList.get(position).getSoporteContactoCelular());
        //holder.txtSoporteContactoTelefonoCelular.setText(searchArrayList.get(position).getSoporteContactoCelular()+"/"+searchArrayList.get(position).getSoporteContactoTelefono());


        return convertView;
    }

    static class ViewHolder {

        TextView txtSoporteContactoNombre;
        //TextView txtSoporteContactoTelefono;
        TextView txtSoporteContactoCelular;
        //TextView txtSoporteContactoTelefonoCelular;


    }
}
