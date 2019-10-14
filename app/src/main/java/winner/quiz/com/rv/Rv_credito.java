package winner.quiz.com.rv;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import winner.quiz.com.Credito;
import winner.quiz.com.R;
import winner.quiz.com.model.CreditoModel;

public class Rv_credito extends RecyclerView.Adapter<Rv_credito.MyViewHoder> {

        Context mContext;
        List<CreditoModel> mData;

        public Rv_credito(Context mContext, List<CreditoModel> mData) {
            this.mContext = mContext;
            this.mData = mData;
        }



        @Override
        public MyViewHoder onCreateViewHolder(ViewGroup viewGroup, int i) {

            View v;
            v = LayoutInflater.from(mContext).inflate(R.layout.row_credito, viewGroup,false);

            MyViewHoder viewHoder = new MyViewHoder(v);

            return viewHoder;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHoder holder, final int position) {
           ///
            holder.tv_code.setText(mData.get(position).getRecarga());
            holder.img.setImageResource(R.drawable.vodacom);

            switch (mData.get(position).getRede()){
                case "vodacom":
                    holder.img.setImageResource(R.drawable.vodacom);
                    break;
                case "movitel":
                    holder.img.setImageResource(R.drawable.movitel);
                    break;
                case "tmcel":
                    holder.img.setImageResource(R.drawable.tmcel);
                    break;
                    default:

            }

        }


        @Override
        public int getItemCount() {
            return mData.size();
        }

        public static class MyViewHoder extends RecyclerView.ViewHolder{


            private TextView tv_code;
            private ImageView img;

            public MyViewHoder(@NonNull View itemView) {
                super(itemView);

                tv_code = itemView.findViewById(R.id.codigo);
                img = itemView.findViewById(R.id.img);


            }
        }

}
