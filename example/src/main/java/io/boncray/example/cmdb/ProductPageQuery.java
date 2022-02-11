package io.boncray.example.cmdb;

import io.boncray.bean.mode.base.PageQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author changan
 * @version 1.0
 * @date 2022/2/11 17:44
 */
@Data
public class ProductPageQuery extends PageQuery {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "宝贝Id")
    private Long itemId;

    @ApiModelProperty(value = "链接-宝贝地址")
    private String itemUrl;

    @ApiModelProperty(value = "商品标题")
    private String title;

    @ApiModelProperty(value = "商品短标题")
    private String shortTitle;

    @ApiModelProperty(value = "商品主图")
    private String mainImage;

    @ApiModelProperty(value = "商品白底图")
    private String whiteImage;

    @ApiModelProperty(value = "商品小图列表")
    private String smallImages;

    @ApiModelProperty(value = "宝贝推广链接")
    private String itemShareUrl;

    @ApiModelProperty(value = "一口价")
    private Double fixedPrice;

    @ApiModelProperty(value = "折扣价（元） 若属于预售商品，付定金时间内，折扣价=预售价")
    private Double discountFixedPrice;

    @ApiModelProperty(value = "是否品牌精选，0不是，1是")
    private Boolean superiorBrand;

    @ApiModelProperty(value = "宝贝所在地")
    private String itemLocation;

    @ApiModelProperty(value = "商品邮费")
    private Double postFee;

    @ApiModelProperty(value = "佣金比例,小数 0.2，就是 20%")
    private Double commissionRate;

    @ApiModelProperty(value = "佣金类型。MKT表示营销计划，SP表示定向计划，COMMON表示通用计划")
    private String commissionType;

    @ApiModelProperty(value = "淘客30天推广量")
    private Integer totalSales;

    @ApiModelProperty(value = "一级分类名称")
    private String levelOneCategoryName;

    @ApiModelProperty(value = "一级分类Id")
    private Long levelOneCategoryId;

    @ApiModelProperty(value = "二级分类名称")
    private String levelTwoCategoryName;

    @ApiModelProperty(value = "二级分类Id")
    private Long levelTwoCategoryId;

    @ApiModelProperty(value = "宝贝描述(推荐理由)")
    private String itemDescription;


}
