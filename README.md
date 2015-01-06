# Android Loading Helper

This is a set of classes which help to perform pull to refresh and endless scrolling in a recycler view.

![Demo GIF](http://raw.github.com/jorgemf/android-loading-helper/master/misc/loadinghelper.gif)

There is a sample application under apptest.

In order to use them add the project to your build.gradle

```Gradle
dependencies {
    compile 'com.livae:android-loadingHelper-v1.0'
}
```

And then in your fragment:

```Java
public class MyFragment extends Fragment implements LoadingHelper.LoadListener {

	private MyAdapter mAdapter;

	private LoadingHelper<MyViewHolder> mLoadingHelper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_loading, container, false);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
		ContentLoadingProgressBar contentLoadingProgressBar =
		        (ContentLoadingProgressBar) view.findViewById(R.id.center_progressbar);
		mAdapter = new MyAdapter();
		mLoadingHelper = new LoadingHelper<MyViewHolder>(
				getActivity(), recyclerView, mAdapter, this, contentLoadingProgressBar,
				new LoadingHelper.ErrorViewsCreator() {

					@Override
					public View createTopErrorView(ViewGroup root) {
					    // return an error view
					}

					@Override
					public View createBottomErrorView(ViewGroup root) {
					    // return an error view
					}

					@Override
					public boolean hasTopErrorView() {
						return true;
					}

					@Override
					public boolean hasBottomErrorView() {
						return true;
					}
				});
		// this enables the initial loading, pull to refresh and endless loading
		mLoadingHelper.enableInitialProgressLoading(true);
		mLoadingHelper.enableEndlessLoading(true);
		mLoadingHelper.enablePullToRefreshUpdate(true);
		mLoadingHelper.start();
	}

	@Override
	public void onResume() {
		super.onResume();
		mLoadingHelper.onResume();
	}

	@Override
	public void loadPrevious() {
	    // load the previous data in the background (pull to refresh)
	    // when finished call:
		// mLoadingHelper.finishLoadingPrevious(boolean error, integer elements added);
	}

	@Override
	public void loadNext() {
	    // load next data in the background (endless loading)
	    // when finished call:
		// mLoadingHelper.finishLoadingNext(boolean error, integer elements added, boolean keep adding more);
	}

	@Override
	public void loadInitial() {
	    // load initial data in the background, make the first network request
	    // when finished call:
		// mLoadingHelper.finishLoadInitial(boolean error, integer elements added, boolean keep adding more);
	}

	@Override
	public void preloadInitial() {
	    // load data from your cache before making any network request
	    // when finished call:
		// mLoadingHelper.finishPreloadInitial();
	}

	@Override
	public void clearAdapter() {
		mAdapter.clear();
	}
}
```