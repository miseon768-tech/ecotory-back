package com.example.ecotory.domain.krwAsset.dto.response.KRWassets;

import com.example.ecotory.domain.krwAsset.entity.KRWAsset;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class KRWAssetByMemberResponse {
    private List<KRWAsset> KRWAssetList;
    private boolean success;
}
