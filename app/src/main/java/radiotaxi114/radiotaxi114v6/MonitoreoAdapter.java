package radiotaxi114.radiotaxi114v6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by USUARIO on 07/05/2015.
 */
public class MonitoreoAdapter extends BaseAdapter {


    private static ArrayList<MonitoreoResults> searchArrayList;

    private LayoutInflater mInflater;

    public MonitoreoAdapter(Context context, ArrayList<MonitoreoResults> results) {
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
            convertView = mInflater.inflate(R.layout.row_monitoreo, null);

            holder = new ViewHolder();
            holder.txtDireccion = (TextView) convertView.findViewById(R.id.CmpMonitoreoDireccion);
            holder.txtReferencia = (TextView) convertView.findViewById(R.id.CmpMonitoreoReferencia);
            holder.txtCambioDe = (TextView) convertView.findViewById(R.id.CmpMonitoreoCambioDe);
            holder.txtTipoUnidad = (TextView) convertView.findViewById(R.id.CmpMonitoreoTipoUnidad);
            holder.txtTipo = (TextView) convertView.findViewById(R.id.CmpMonitoreoTipo);

            holder.lblReferencia = (TextView) convertView.findViewById(R.id.LblMonitoreoReferencia);
            holder.lblTipoUnidad = (TextView) convertView.findViewById(R.id.LblMonitoreoTipoUnidad);
            holder.lblCambioDe = (TextView) convertView.findViewById(R.id.LblMonitoreoCambioDe);

            holder.lblTipo = (TextView) convertView.findViewById(R.id.LblMonitoreoTipo);

            holder.imgFoto = (ImageView) convertView.findViewById(R.id.ImgMonitoreoTipo);

            holder.capMonitoreoTipoUnidad = (LinearLayout) convertView.findViewById(R.id.CapMonitoreoTipoUnidad);
            holder.capMonitoreoCambioDe = (LinearLayout) convertView.findViewById(R.id.CapMonitoreoCambioDe);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDireccion.setText(searchArrayList.get(position).getPedidoDireccion());
        holder.txtReferencia.setText(searchArrayList.get(position).getPedidoReferencia());
        holder.txtTipoUnidad.setText(searchArrayList.get(position).getPedidoTipoUnidad());
        holder.txtCambioDe.setText(searchArrayList.get(position).getPedidoCambioDe());
        holder.txtTipo.setText("");

        /*if(searchArrayList.get(position).getPedidoDetalle().equals("") || searchArrayList.get(position).getPedidoDetalle() == null || searchArrayList.get(position).getPedidoDetalle().equals("null")) {

            holder.txtTipo.setText("");
            holder.txtTipo.setVisibility(View.GONE);
            holder.lblTipo.setVisibility(View.GONE);

        }else{

            holder.txtTipo.setText("COMPRA Y OTROS");
            holder.txtTipo.setVisibility(View.VISIBLE);
            holder.lblTipo.setVisibility(View.VISIBLE);

        }*/

        if(!searchArrayList.get(position).getPedidoDetalle().equals("") && searchArrayList.get(position).getPedidoDetalle() != null && !searchArrayList.get(position).getPedidoDetalle().equals("null")) {

            holder.txtTipo.setText("COMPRA Y OTROS");
            holder.txtTipo.setVisibility(View.VISIBLE);
            holder.lblTipo.setVisibility(View.VISIBLE);


        }else{

            holder.txtTipo.setText("");
            holder.txtTipo.setVisibility(View.GONE);
            holder.lblTipo.setVisibility(View.GONE);

        }

      /*
        if(!searchArrayList.get(position).getPedidoDetalle().equals("")){
            holder.imgMonitoreoDetalle.setVisibility(View.VISIBLE);
        }else{
            holder.imgMonitoreoDetalle.setVisibility(View.GONE);
        }
*/
        if(searchArrayList.get(position).getPedidoTipoUnidad().equals("") || searchArrayList.get(position).getPedidoTipoUnidad() == null || searchArrayList.get(position).getPedidoTipoUnidad().equals("null")) {
            holder.txtTipoUnidad.setVisibility(View.GONE);
            holder.lblTipoUnidad.setVisibility(View.GONE);
            holder.capMonitoreoTipoUnidad.setVisibility(View.GONE);
        }else {
            holder.txtTipoUnidad.setVisibility(View.VISIBLE);
            holder.lblTipoUnidad.setVisibility(View.VISIBLE);
            holder.capMonitoreoTipoUnidad.setVisibility(View.VISIBLE);
        }

        if(searchArrayList.get(position).getPedidoCambioDe().equals("") || searchArrayList.get(position).getPedidoCambioDe() == null || searchArrayList.get(position).getPedidoCambioDe().equals("null")) {
            holder.txtCambioDe.setVisibility(View.GONE);
            holder.lblCambioDe.setVisibility(View.GONE);
            holder.capMonitoreoCambioDe.setVisibility(View.GONE);
        }else {
            holder.txtCambioDe.setVisibility(View.VISIBLE);
            holder.lblCambioDe.setVisibility(View.VISIBLE);
            holder.capMonitoreoCambioDe.setVisibility(View.VISIBLE);
        }

        if(searchArrayList.get(position).getPedidoReferencia().equals("") || searchArrayList.get(position).getPedidoReferencia() == null || searchArrayList.get(position).getPedidoReferencia().equals("null")) {
            holder.txtReferencia.setVisibility(View.GONE);
            holder.lblReferencia.setVisibility(View.GONE);
        }else {
            holder.txtReferencia.setVisibility(View.VISIBLE);
            holder.lblReferencia.setVisibility(View.VISIBLE);
        }


        if(searchArrayList.get(position).getPedidoTipo().equals("1") || searchArrayList.get(position).getPedidoTipo().equals("11") ) {

            if(searchArrayList.get(position).getClienteVerificado().equals("1")){
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreob150);
            }else{
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreo150);
            }

        }else if(searchArrayList.get(position).getPedidoTipo().equals("7")  || searchArrayList.get(position).getPedidoTipo().equals("10")) {

            if (searchArrayList.get(position).getClienteVerificado().equals("1")) {
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreoe150);
            } else {
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreof150);
            }

        }else if(searchArrayList.get(position).getPedidoTipo().equals("6") ) {

            if(searchArrayList.get(position).getClienteVerificado().equals("1")){
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreoc150);
            }else{
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreod150);
            }

        }else if( searchArrayList.get(position).getPedidoTipo().equals("9") || searchArrayList.get(position).getPedidoTipo().equals("13") ) {

            if(searchArrayList.get(position).getClienteVerificado().equals("1")){
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreog150);
            }else{
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreoh150);
            }

        }else{

            if(searchArrayList.get(position).getClienteVerificado().equals("1")){
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreob150);
            }else{
                holder.imgFoto.setImageResource(R.mipmap.icon_monitoreo150);
            }

        }



        return convertView;
    }

    static class ViewHolder {

        TextView lblTipoUnidad;
        TextView lblReferencia;
        TextView lblDetalle;
        TextView lblCambioDe;
        TextView lblTipo;

        TextView txtDireccion;
        TextView txtTipoUnidad;
        TextView txtReferencia;
        TextView txtDetalle;
        TextView txtCambioDe;
        TextView txtTipo;

        LinearLayout capMonitoreoTipoUnidad;
        LinearLayout capMonitoreoCambioDe;

        ImageView imgFoto;

    }


}
