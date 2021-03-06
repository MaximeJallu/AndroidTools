package {package_name}

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.oxylane.android.cubeinstore.data.interfaces.IBaseCommunication
import com.oxylane.android.cubeinstore.data.interfaces.IViewType
import com.oxylane.android.cubeinstore.ui.fragment.interfaces.IAdapterChanged
import com.oxylane.android.cubeinstore.ui.viewholder.RecyclerViewHolder
import java.security.AccessControlException
import java.util.*

/**
 * @author Maxime Jallu
 * @since 03/05/2017
 *
 * Use this Class for : <br></br>
 * ... {DOCUMENTATION}
 */
open class RecyclerAdapter<T> : RecyclerView.Adapter<RecyclerViewHolder<T>> {

    private var mTList: MutableList<T>
    private var mFactory: ViewHolderFactory<T>? = null
    private var mAdapterChanged: IAdapterChanged? = null

    constructor() {
        mTList = ArrayList()
    }

    constructor(factory: ViewHolderFactory<T>) : this(ArrayList<T>(), factory, null)

    constructor(viewHolderType: Class<out RecyclerViewHolder<T>>) : this(ArrayList<T>(), viewHolderType, null)

    constructor(viewHolderType: Class<out RecyclerViewHolder<T>>, callback: IBaseCommunication<*>?) : this(ArrayList<T>(), viewHolderType, callback)

    @JvmOverloads constructor(TList: MutableList<T>, viewHolderType: Class<out RecyclerViewHolder<T>>, callback: IBaseCommunication<*>? = null) : this(TList, ViewHolderFactory<T>(viewHolderType), callback)

    constructor(TList: MutableList<T>, factory: ViewHolderFactory<T>, callback: IBaseCommunication<*>?) {
        mTList = TList
        mFactory = factory
        mFactory!!.communication = callback
    }

    open fun setFactory(factory: ViewHolderFactory<T>) {
        mFactory = factory
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder<T>? {
        if (mFactory == null) {
            throw AccessControlException("mFactory is not instancied. thanks use setFactory() method.")
        }

        return mFactory!!.createVH(LayoutInflater.from(parent.context)
                .inflate(mFactory!!.getLayoutRes(viewType), parent, false), viewType)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder<T>, position: Int) {
        holder.item = getItem(position)
        holder.isBound = false
        holder.bind(holder.item!!)
        holder.isBound = true
    }

    override fun getItemCount(): Int {
        return mTList.size
    }

    /**
     * Get Item
     *
     * @param position position founded
     * @return instance to position
     */
    fun getItem(position: Int): T {
        return mTList[position]
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position) != null && getItem(position) is IViewType) {
            (getItem(position) as IViewType).itemViewType
        } else super.getItemViewType(position)
    }

    fun putViewType(viewType: Int, viewHolder: Class<out RecyclerViewHolder<T>>) {
        mFactory!!.putViewType(viewType, viewHolder)
    }

    operator fun contains(obj: T): Boolean {
        return mTList.contains(obj)
    }

    protected fun callChangedListener() {
        if (mAdapterChanged != null) {
            mAdapterChanged!!.onItemCountChange(itemCount)
        }
    }

    fun setOnAdapterChangedListener(adapterChanged: IAdapterChanged) {
        mAdapterChanged = adapterChanged
    }

    fun setCommunication(communication: IBaseCommunication<*>) {
        mFactory!!.communication = communication
        notifyDataSetChanged()
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param item element to be inserted
     */
    fun addItem(item: T) {
        mTList.add(item)
        notifyItemInserted(mTList.size)
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param item     element to be inserted
     * @param position index at which the specified element is to be inserted
     */
    fun addItem(item: T, position: Int) {
        val pos = Math.min(position, mTList.size)
        mTList.add(pos, item)
        notifyItemInserted(pos)
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param collection elements to be inserted
     */
    fun addAll(collection: List<T>) {
        mTList.addAll(collection)
        val start = Math.max(0, mTList.size - collection.size - 1)
        notifyItemRangeInserted(start, collection.size)
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param item the element to be removed
     */
    fun removeItem(item: T) {
        removeItem(tList.indexOf(item))
    }

    /**
     * Inserts the specified element at the specified position in this list (optional operation).
     * Shifts the element currently at that position (if any) and any subsequent elements to the right (adds one to their indices).
     *
     * @param position the index of the element to be removed
     */
    fun removeItem(position: Int) {
        if (position > -1 && position < mTList.size) {
            mTList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    /**
     * Set new list items and notifyDataSetChanged()
     *
     * @param list new instance items list for bind views
     * @link notifyDataSetChanged
     */
    fun updateItems(list: MutableList<T>) {
        mTList = list
        notifyDataSetChanged()
    }

    /**
     * @return instance items list
     */
    val tList: List<T>
        get() = mTList

    val isEmpty: Boolean
        get() = mTList.isEmpty()
}
