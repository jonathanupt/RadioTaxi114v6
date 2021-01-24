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
public class OperadorasAdapter extends BaseAdapter {

    private static ArrayList<OperadorasResults> searchArrayList;

    private LayoutInflater mInflater;

    public OperadorasAdapter(Context context, ArrayList<OperadorasResults> results) {
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

            convertView = mInflater.inflate(R.layout.row_operadoras, null);

            holder = new ViewHolder();

            holder.txtOperadoraNombre = (TextView) convertView.findViewById(R.id.CmpOperadorasNombre);
            holder.imgActivo = (ImageView) convertView.findViewById(R.id.ImgOperadorasActivo);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.txtOperadoraNombre.setText(searchArrayList.get(position).getOperadoraNombre());

        if(searchArrayList.get(position).getOperadoraActivo().equals("1")){
            holder.imgActivo.setImageResource(R.mipmap.icon_operadora150);
        }else{
            holder.imgActivo.setImageResource(R.mipmap.icon_operadora_inactiva150);
        }


        return convertView;
    }

    static class ViewHolder {

        TextView txtOperadoraNombre;
        ImageView imgActivo;

    }


}
