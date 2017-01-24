package by.mksn.epam.mentalaid.service;

import by.mksn.epam.mentalaid.entity.Entity;
import by.mksn.epam.mentalaid.service.exception.ServiceException;

import java.util.List;

/**
 * Interface to select lists with built-in pagination support
 *
 * @param <E>
 */
public interface ItemPageService<E extends Entity> {

    int ITEMS_PER_PAGE = 10;

    /**
     * Selects page of items with specified index
     *
     * @param index        index of the page
     * @param itemsPerPage maximum count of items on the single page
     * @return {@link ItemsPage} entity of the current page
     * @throws ServiceException if error happens during execution
     * @see #getPage(int)
     */
    ItemsPage<E> getPage(int index, int itemsPerPage) throws ServiceException;

    /**
     * Selects page of items with specified index and maximum count of
     * items on the single page equals to {@link #ITEMS_PER_PAGE}
     *
     * @param index index of the page
     * @return {@link ItemsPage} entity of the current page
     * @throws ServiceException if error happens during execution
     * @see #getPage(int, int)
     */
    default ItemsPage<E> getPage(int index) throws ServiceException {
        return getPage(index, ITEMS_PER_PAGE);
    }

    /**
     * Selects page of searched items according searchQuery with specified index
     *
     * @param index        index of the page
     * @param itemsPerPage maximum count of items on the single page
     * @param searchQuery  query to search
     * @return {@link ItemsPage} entity of the current page
     * @throws ServiceException if error happens during execution
     * @see #getSearchPage(int, String)
     */
    ItemsPage<E> getSearchPage(int index, int itemsPerPage, String searchQuery) throws ServiceException;

    /**
     * Selects page of searched items according searchQuery with specified index and maximum count of
     * items on the single page equals to {@link #ITEMS_PER_PAGE}
     *
     * @param index       index of the page
     * @param searchQuery query to search
     * @return {@link ItemsPage} entity of the current page
     * @throws ServiceException if error happens during execution
     * @see #getSearchPage(int, int, String)
     */
    default ItemsPage<E> getSearchPage(int index, String searchQuery) throws ServiceException {
        return getSearchPage(index, ITEMS_PER_PAGE, searchQuery);
    }

    /**
     * Represents UI page with item list.
     *
     * @param <E> {@link Entity} ot the list
     */
    final class ItemsPage<E> {

        private List<E> items;
        private int pageCount;
        private int currentIndex;

        public ItemsPage(List<E> items, int pageCount, int currentIndex) {
            this.items = items;
            this.pageCount = pageCount;
            this.currentIndex = currentIndex;
        }

        /**
         * @return List of entities on the current page
         */
        public List<E> getItems() {
            return items;
        }

        /**
         * @return all available page count
         */
        public int getPageCount() {
            return pageCount;
        }

        /**
         * @return real current index of this page
         */
        public int getCurrentIndex() {
            return currentIndex;
        }
    }
}
