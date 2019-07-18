package xifuyin.tumour.com.a51ehealth.kotlin_simple.baseadapter;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;



/**
 * 描述: RecyclerView通用分割线
 * @author Smile
 */
@SuppressWarnings("unused")
public class Divider extends RecyclerView.ItemDecoration {
    /*线的图片*/
    private Drawable dividerDrawable;
    /*默认宽*/
    private final int DEFAULT_LINE_WIDTH = 10;
    /*默认高度*/
    private final int DEFAULT_LINE_HEIGHT = 20;

    private int lineWidth = DEFAULT_LINE_WIDTH;// 线的宽度
    private int lineHeight = DEFAULT_LINE_HEIGHT;// 线的高度
    private int headerCount = 0;// 头的数量
    private int footerCount = 0;// 尾的数量

    Divider() {
        /*线默认的图片*/
        dividerDrawable = new ColorDrawable(Color.GRAY);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (isSkipDraw(parent, view))
            return;// 跳过头和尾，不绘制
        /*获取当前view的位置*/
        int currentPosition = parent.getChildAdapterPosition(view);
        // 水平个数，线性布局为-1
        int spanCount = getSpanCount(parent);
        // 总个数
        int childCount = parent.getAdapter().getItemCount();
        /*线的宽度*/
        int right = lineWidth;
        /*线的高*/
        int bottom = lineHeight;
        if (isNotDrawBottom(view, parent, currentPosition, spanCount, childCount))
            // 如果是最后一行，则不需要绘制底部
            bottom = 0;
        if (isNotDrawRight(view, parent, currentPosition, spanCount, childCount))
            // 如果是最后一列，则不需要绘制右边
            right = 0;
        outRect.set(0, 0, right, bottom);
    }


    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(canvas, parent, lineWidth, lineHeight);
        drawVertical(canvas, parent, lineWidth, lineHeight);
    }

    /**
     * 是否不绘制右部
     *
     * @param view            当前的view，StaggeredGridLayoutManager 用
     * @param parent          RecyclerView
     * @param currentPosition 当前的位置，GridLayoutManager、LinearLayoutManager用
     * @param spanCount       列数
     * @param adapterCount    adapter的总数
     * @return 返回true代表不绘制右部，返回false，代表绘制右部
     */
    @SuppressLint("WrongConstant")
    private boolean isNotDrawRight(View view, RecyclerView parent, int currentPosition, int spanCount, int adapterCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // GridLayoutManager
            currentPosition -= getHeaderCount();// 去掉头的数量
            adapterCount -= getHeaderCount() + getFooterCount();// 去掉头、尾的数量
            // 判断最后一个是否绘制
            if (((GridLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                // 垂直，判断是否是最后一列
                return (currentPosition + 1) % spanCount == 0;
            } else {
                // 水平，判断是不是最后的
                if (adapterCount % spanCount == 0)
                    return currentPosition >= adapterCount - spanCount;
                else
                    return currentPosition >= adapterCount - adapterCount % spanCount;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            // LinearLayoutManager
            // 判断最后一个是否绘制，垂直，不绘制右边，直接返回true,水平，判断，是否是最后一个
            return ((LinearLayoutManager) layoutManager).getOrientation() == LinearLayout.VERTICAL || currentPosition == adapterCount - getFooterCount() - 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            // 判断最后一个是否绘制，垂直，判断是否是最后一列，是返回true，水平，都显示，返回false
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() == StaggeredGridLayoutManager.VERTICAL && (lp.getSpanIndex() + 1) % spanCount == 0;
        }
        return false;
    }

    /**
     * 是否不绘制底部
     *
     * @param view            当前的view，StaggeredGridLayoutManager 用
     * @param parent          RecyclerView
     * @param currentPosition 当前的位置，GridLayoutManager、LinearLayoutManager用
     * @param spanCount       列数
     * @param adapterCount    adapter的总数
     * @return 返回true代表不绘制底部，返回false，代表绘制底部
     */
    @SuppressLint("WrongConstant")
    private boolean isNotDrawBottom(View view, RecyclerView parent, int currentPosition, int spanCount, int adapterCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // GridLayoutManager
            currentPosition -= getHeaderCount();// 去掉头的数量
            adapterCount -= getHeaderCount() + getFooterCount();// 去掉头、尾的数量
            // 判断最后一个是否绘制
            if (((GridLayoutManager) layoutManager).getOrientation() == LinearLayoutManager.VERTICAL) {
                // 垂直，判断是不是最后的
                if (adapterCount % spanCount == 0)
                    return currentPosition >= adapterCount - spanCount;
                else
                    return currentPosition >= adapterCount - adapterCount % spanCount;

            } else {
                // 水平，判断是不是最后一列
                return (currentPosition + 1) % spanCount == 0;
            }
        } else if (layoutManager instanceof LinearLayoutManager) {
            // LinearLayoutManager
            // 判断最后一个是否绘制，垂直，判断是否是最后一行,水平，直接返回true，不绘制底部
            return ((LinearLayoutManager) layoutManager).getOrientation() != LinearLayout.VERTICAL || currentPosition == adapterCount - getFooterCount() - 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            // StaggeredGridLayoutManager
            // 判断最后一个是否绘制，垂直，都显示，返回false， 水平，判断是否是最后一列，是返回true
            StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation() != StaggeredGridLayoutManager.VERTICAL && (lp.getSpanIndex() + 1) % spanCount == 0;
        }
        return false;
    }

    /**
     * 绘制水平线
     *
     * @param canvas     画布
     * @param parent     RecyclerView
     * @param lineWidth  线宽
     * @param lineHeight 线高
     */
    private void drawHorizontal(Canvas canvas, RecyclerView parent, int lineWidth, int lineHeight) {
        boolean isDrawDoubleLine = false;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager && ((StaggeredGridLayoutManager) layoutManager).getOrientation() == StaggeredGridLayoutManager.HORIZONTAL)
            // 绘制双线
            isDrawDoubleLine = true;
        canvas.save();
        int spanCount = getSpanCount(parent);// 水平个数，线性布局为-1
        // 显示的个数
        int childCount = parent.getChildCount();
        // 总个数
        int adapterCount = parent.getAdapter().getItemCount();

        if (parent.getClipToPadding()) {
            canvas.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(),
                    parent.getWidth() - parent.getPaddingRight(),
                    parent.getHeight() - parent.getPaddingBottom());
        }

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int currentPosition = parent.getChildAdapterPosition(child);
            if (isSkipDraw(parent, child))
                // 跳过，直接返回
                continue;
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (!isNotDrawBottom(child, parent, currentPosition, spanCount, adapterCount)) {
                // 绘制底部
                int bottomLineWidth = isNotDrawRight(child, parent, currentPosition, spanCount, adapterCount) ? 0 : lineWidth;// 不绘制右部，公共区域不绘制
                // 绘制下线
                final int downLeft = child.getLeft() - params.leftMargin;
                final int downTop = child.getBottom() + params.bottomMargin;
                final int downRight = child.getRight() + params.rightMargin + bottomLineWidth;// 公共区域绘制
                final int downBottom = downTop + lineHeight;
                dividerDrawable.setBounds(downLeft, downTop, downRight, downBottom);
                dividerDrawable.draw(canvas);
            }
            // 判断是否绘制双线
            if (isDrawDoubleLine && isStaggeredGridNotFirstView(child, spanCount)) {
                // 绘制上线
                final int upLeft = child.getLeft() - params.leftMargin;
                final int upTop = child.getTop() + params.topMargin - lineHeight;
                final int upRight = child.getRight() + params.rightMargin + lineWidth;// 公共区域绘制
                final int upBottom = upTop + lineHeight;
                dividerDrawable.setBounds(upLeft, upTop, upRight, upBottom);
                dividerDrawable.draw(canvas);
            }
        }
        canvas.restore();
    }

    /**
     * 绘制垂直线
     *
     * @param canvas     画布
     * @param parent     RecyclerView
     * @param lineWidth  线宽
     * @param lineHeight 线高
     */
    private void drawVertical(Canvas canvas, RecyclerView parent, int lineWidth, int lineHeight) {
        boolean isDrawDoubleLine = false;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager && ((StaggeredGridLayoutManager) layoutManager).getOrientation() == StaggeredGridLayoutManager.VERTICAL)
            // 绘制双线
            isDrawDoubleLine = true;
        canvas.save();
        if (parent.getClipToPadding()) {
            canvas.clipRect(parent.getPaddingLeft(), parent.getPaddingTop(),
                    parent.getWidth() - parent.getPaddingRight(),
                    parent.getHeight() - parent.getPaddingBottom());
        }
        int spanCount = getSpanCount(parent);// 水平个数，线性布局为-1

        int childCount = parent.getChildCount();
        int adapterCount = parent.getAdapter().getItemCount();// 总个数
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            int currentPosition = parent.getChildAdapterPosition(child);
            if (isSkipDraw(parent, child))
                // 跳过、不绘制右部，直接返回
                continue;
            final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
            if (!isNotDrawRight(child, parent, currentPosition, spanCount, adapterCount)) {
                // 不绘制右边
                if (isNotDrawBottom(child, parent, currentPosition, spanCount, adapterCount))
                    // 不绘制底部，公共区域不绘制
                    lineHeight = 0;
                final int left = child.getRight() + params.rightMargin;
                final int top = child.getTop() - params.topMargin;
                final int right = left + lineWidth;
                final int bottom = child.getBottom() + params.bottomMargin + lineHeight;// 公共区域水平绘制
                dividerDrawable.setBounds(left, top, right, bottom);
                dividerDrawable.draw(canvas);
            }
            // 判断是否绘制双线
            if (isDrawDoubleLine && isStaggeredGridNotFirstView(child, spanCount)) {
                // 绘制左线
                final int left = child.getLeft() + params.leftMargin - lineWidth;
                final int top = child.getTop() - params.topMargin;
                final int right = left + lineWidth;
                final int bottom = child.getBottom() + params.bottomMargin + lineHeight;// 公共区域水平绘制
                dividerDrawable.setBounds(left, top, right, bottom);
                dividerDrawable.draw(canvas);
            }
        }
        canvas.restore();
    }

    /**
     * 是否是StaggeredGridLayoutManager的中间的view
     *
     * @param view      测定的view
     * @param spanCount 列数
     */
    private boolean isStaggeredGridNotFirstView(View view, int spanCount) {
        StaggeredGridLayoutManager.LayoutParams lp = (StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams();
        return lp.getSpanIndex() != 0;
    }

    /**
     * 是否跳过绘画
     *
     * @param parent RecyclerView
     * @param view   当前View
     */
    private boolean isSkipDraw(RecyclerView parent, View view) {
        /*获取当前view的位置*/
        int currentPosition = parent.getChildAdapterPosition(view);
        // 当前item总位置
        int adapterCount = parent.getAdapter().getItemCount();
          //不绘制头和脚
        return currentPosition < getHeaderCount() || currentPosition >= adapterCount - getFooterCount();
    }

    /**
     * 获取列数
     *
     * @param parent RecyclerView
     * @return 列数
     */
    private int getSpanCount(RecyclerView parent) {
        // 列数
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof LinearLayoutManager) {
            spanCount = 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 获取线宽
     */
    public int getLineWidth() {
        return lineWidth;
    }

    /**
     * 设置线宽
     */
    public void setLineWidth(int lineWidth) {
        this.lineWidth = lineWidth;
    }

    /**
     * 获取线高
     */
    public int getLineHeight() {
        return lineHeight;
    }

    /**
     * 设置线高
     */
    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    /**
     * 获取线Drawable
     */
    public Drawable getDividerDrawable() {
        return dividerDrawable;
    }

    /**
     * 设置线Drawable，和setLineColor()二选一
     */
    public void setDividerDrawable(Drawable dividerDrawable) {
        this.dividerDrawable = dividerDrawable;
    }

    /**
     * 设置线颜色，和setDividerDrawable()二选一
     */
    public void setLineColor(int lineColor) {
        this.dividerDrawable = new ColorDrawable(lineColor);
    }

    /**
     * 获取头数量
     */
    private int getHeaderCount() {
        return headerCount;
    }

    /**
     * 设置头数量，即头部跳过绘制
     */
    public void setHeaderCount(int headerCount) {
        this.headerCount = headerCount;
    }

    /**
     * 获取尾数量
     */
    private int getFooterCount() {
        return footerCount;
    }

    /**
     * 设置尾数量，即尾部跳过绘制
     */
    public void setFooterCount(int footerCount) {
        this.footerCount = footerCount;
    }

    /**
     * Divider的构建者
     */
    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final Divider divider;

        Builder() {
            divider = new Divider();
        }

        /**
         * 设置线宽
         */
        public Builder width(int lineWidth) {
            divider.setLineWidth(lineWidth);
            return this;
        }

        /**
         * 设置线高
         */
        public Builder height(int lineHeight) {
            divider.setLineHeight(lineHeight);
            return this;
        }

        /**
         * 同时设置线宽、线高
         */
        public Builder widthAndHeight(int lineSize) {
            divider.setLineWidth(lineSize);
            divider.setLineHeight(lineSize);
            return this;
        }

        /**
         * 设置线颜色，和drawable二选一
         */
        public Builder color(int lineColor) {
            divider.setLineColor(lineColor);
            return this;
        }

        /**
         * 设置线背景，和color二选一
         */
        public Builder drawable(Drawable dividerDrawable) {
            divider.setDividerDrawable(dividerDrawable);
            return this;
        }

        /**
         * 设置头的数量
         */
        public Builder headerCount(int headerCount) {
            divider.setHeaderCount(headerCount);
            return this;
        }

        /**
         * 设置尾的数量
         */
        public Builder footerCount(int footerCount) {
            divider.setFooterCount(footerCount);
            return this;
        }

        /**
         * 返回Divider
         */
        public Divider build() {
            return this.divider;
        }

    }
}
