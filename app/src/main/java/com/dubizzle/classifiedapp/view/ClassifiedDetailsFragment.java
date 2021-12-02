package com.dubizzle.classifiedapp.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.dubizzle.classifiedapp.R;
import com.dubizzle.classifiedapp.databinding.FragmentDetailsBinding;
import com.dubizzle.classifiedapp.model.ClassifiedResults;
import com.dubizzle.classifiedapp.utils.Constants;
import com.dubizzle.classifiedapp.utils.DateUtils;
import com.dubizzle.dubicache.core.DubiCache;
import dagger.hilt.android.AndroidEntryPoint;

// Note : This class is written in Java [as requested]
@AndroidEntryPoint
public class ClassifiedDetailsFragment extends Fragment {

    private DubiCache imageLoader;
    private FragmentDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupUI();
    }

    private void setupUI() {
        if(getArguments() != null) {
            ClassifiedResults selectedItem = getArguments().getParcelable(Constants.KEY_GLASSIFIED);
            imageLoader = DubiCache.Companion.getInstance(requireContext(), Constants.CACHE_SIZE);
            if(binding != null) {
                if(selectedItem != null) {
                    binding.txtNameClassified.setText(selectedItem.getName());
                    binding.txtPriceClassified.setText(selectedItem.getPrice());
                    binding.txtCreatedAt.setText(new DateUtils().getFormattedDateTimeToDisplay(selectedItem.getCreated_at()));
                    if(imageLoader != null)
                        imageLoader.displayImage(selectedItem.getImage_urls().get(0), binding.imgViewClassified, R.drawable.ic_gift);
                }
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(imageLoader != null)
            imageLoader.clearcache();
        binding = null;
    }
}