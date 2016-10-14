# DividerItemDecoration
Android - DividerItemDecoration - RecyclerView


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
        public TrainingViewHolder(View itemView) {
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
