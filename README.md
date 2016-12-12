# Android : Recycler Tools
- ArrayAdapter
- ItemDecoration (Divider - Header - Footer) 


# ArrayRecyclerAdapter
Sample : 
```java
public class SampleAdapter extends ArrayRecyclerAdapter<String,SampleAdapter.ItemViewHolder> {

    public SampleAdapter(@NonNull List<String> list) {
        super(list);
    }

    @Override public SampleAdapter onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(R.layout.training_viewholder, parent, false));
    }
    /**
     * Training ViewHolder
     */
    class ItemViewHolder extends RecyclerViewHolder<String> {

        @BindView(R.id.training_note_label)
        TextView mTextView;

        /**
         * This super() auto BindViews with ButterKnife<br/>
         *
         * @param itemView the Views holder
         */
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        /**
         * Update the view with data
         *
         * @param note data
         */
        @Override
        public void bind(String note) {
            mTextView.setText(note);
        }
    }
}
```

# ItemDecoration
```java
mRecyclerView.addItemDecoration(new FooterDecoration(getContext(), this, R.layout.item_space_80));
```
