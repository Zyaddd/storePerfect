package com.example.storePerfect;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.storePerfect.Interface.ItemClickListener;


public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


    public TextView txtProductName, txtProductDescription, txtProductPrice;
    public ImageView imageView;
    public ItemClickListener listener;

    public ProductViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.product_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product_name);
        txtProductDescription = (TextView) itemView.findViewById(R.id.product_description);
        txtProductPrice = (TextView) itemView.findViewById(R.id.product_price);


    }

    public void setItemClickListener(ItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
//        listener.onClick(view, getAdapterPosition(), false);
    }

//    public void setDetails(Context context, String title, String image) {
//        TextView mTitleName = view.findViewById(R.id.textView_name);
//        ImageView mImageView = view.findViewById(R.id.image_view_product);
//
//        mTitleName.setText(title);
//        Picasso.get().load(image).into(mImageView);
//
//        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//        itemView.startAnimation(animation);
//
//    }
}
