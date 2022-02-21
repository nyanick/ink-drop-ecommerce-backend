package com.springboot.ecommerce.controller;

import com.springboot.ecommerce.model.Product;
import com.springboot.ecommerce.model.ProductOptions;
import com.springboot.ecommerce.service.ProductService;
import com.springboot.ecommerce.service.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import org.json.JSONArray;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/product")
@Tag(name = "Product API")
public class ProductController {

    @Autowired
    ProductService productService;

    @Operation(summary = "Creates new product", description = "Creates new product",tags = { "Product" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Product created",content = @Content(schema = @Schema(example = " ")))})
    @PostMapping(value = "/product", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<Void> createNewProduct(@RequestParam List<ProductOptions> poption, @RequestParam List<String> category,@RequestParam List<String> subcategory,@RequestParam MultiValueMap<String, Object> data,@RequestParam MultipartFile coverimage,@RequestParam List<MultipartFile> media,  UriComponentsBuilder ucBuilder){
        Product product = productService.createProduct(poption,category,subcategory,data,coverimage,media);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @Operation(summary = "Updates the product which is specified by id", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated Product Category", content = @Content(schema = @Schema(example = "{\n" +
                    "    \"id\": product_id,\n" +
                    "    \"name\": \"string\",\n" +
                    "    \"shortdescription\": \"string\",\n" +
                    "    \"longdescription\": \"string\",\n" +
                    "    \"price\": float,\n" +
                    "    \"quantiry\": int,\n" +
                    "    \"createdDate\": string,\n" +
                    "    \"modifiedDate\": string,\n" +
                    "    \"category\": [\n id_1, id_2" +
                    "    ],\n" +
                    "    \"subcategory\": [\n id_1, id_2" +
                    "    ],\n" +
                    "    \"coverImage\": {\n" +
                    "        \"id\": cover_image_id_of_the_product,\n" +
                    "        \"url\": \"string\"\n" +
                    "        \"size\": \"string\"\n" +
                    "        \"type\": \"string\"\n" +
                    "    },\n" +
                    "    \"customprices\": string,\n" +
                    "    \"unitAreaPrice\": string,\n" +
                    "    \"itemSet\": [\n" +
                    "        {\n" +
                    "            \"id\": id_item,\n" +
                    "            \"width\": int\n" +
                    "            \"length\": int\n" +
                    "            \"height\": int\n" +
                    "            \"price\": Double\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": id_item,\n" +
                    "            \"width\": int\n" +
                    "            \"length\": int\n" +
                    "            \"height\": int\n" +
                    "            \"price\": Double\n" +
                    "        }\n" +
                    "    ]\n" +
                    "    \"media\": [\n" +
                    "        {\n" +
                        "        \"id\": image_id_of_the_product,\n" +
                        "        \"url\": \"string\"\n" +
                        "        \"size\": \"string\"\n" +
                        "        \"type\": \"string\"\n" +
                    "        },\n" +
                    "        {\n" +
                        "        \"id\": image_id_of_the_product,\n" +
                        "        \"url\": \"string\"\n" +
                        "        \"size\": \"string\"\n" +
                        "        \"type\": \"string\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}"))),
            @ApiResponse(responseCode = "500", description = "No elements with ID that you have specified",content = @Content(schema = @Schema(example = " ")))
    })
    @PostMapping(value="/updateproduct/")
    public Product updateProduct(@RequestBody Product product)
    {
        return  productService.editProduct(product);
    }

    @Operation(summary = "Deletes the product which is specified by id", description = "Deletes product", tags = { "Product" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Deletes Specified Product", content = @Content(schema = @Schema(example = " "))) })
    @GetMapping(value="/deleproduct/{id}", headers ="Accept=application/json")
    public ResponseEntity<Product> deleteProduct(@Parameter(description = "ID of the product") @PathVariable("id") Long id){
        String dele = "0";
        Product product = productService.findProductById(id,dele);
        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        product.setDele("1");
        productService.saveProduct(product);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }
    
    @Operation(summary = "Desactivate the product which is specified by id", description = "Deletes product", tags = { "Product" })
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Desactivate Specified Product", content = @Content(schema = @Schema(example = " "))) })
    @GetMapping(value="/desactivateproduct/{id}", headers ="Accept=application/json")
    public ResponseEntity<Product> desactivateProduct(@Parameter(description = "ID of the product") @PathVariable("id") Long id){
        String dele = "0";
        Product product = productService.findProductById(id,dele);
        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        product.setStatus("2");
        productService.saveProduct(product);
        return new ResponseEntity<Product>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Find product by ID", description = "Returns a single product", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation", content = @Content(schema = @Schema(example = "{\n" +
                    "    \"id\": product_id,\n" +
                    "    \"name\": \"string\",\n" +
                    "    \"shortdescription\": \"string\",\n" +
                    "    \"longdescription\": \"string\",\n" +
                    "    \"price\": float,\n" +
                    "    \"quantiry\": int,\n" +
                    "    \"createdDate\": string,\n" +
                    "    \"modifiedDate\": string,\n" +
                    "    \"category\": [\n id_1, id_2" +
                    "    ],\n" +
                    "    \"subcategory\": [\n id_1, id_2" +
                    "    ],\n" +
                    "    \"coverImage\": {\n" +
                    "        \"id\": cover_image_id_of_the_product,\n" +
                    "        \"url\": \"string\"\n" +
                    "        \"size\": \"string\"\n" +
                    "        \"type\": \"string\"\n" +
                    "    },\n" +
                    "    \"customprices\": string,\n" +
                    "    \"unitAreaPrice\": string,\n" +
                    "    \"itemSet\": [\n" +
                    "        {\n" +
                    "            \"id\": id_item,\n" +
                    "            \"width\": int\n" +
                    "            \"length\": int\n" +
                    "            \"height\": int\n" +
                    "            \"price\": Double\n" +
                    "        },\n" +
                    "        {\n" +
                    "            \"id\": id_item,\n" +
                    "            \"width\": int\n" +
                    "            \"length\": int\n" +
                    "            \"height\": int\n" +
                    "            \"price\": Double\n" +
                    "        }\n" +
                    "    ]\n" +
                    "    \"media\": [\n" +
                    "        {\n" +
                        "        \"id\": image_id_of_the_product,\n" +
                        "        \"url\": \"string\"\n" +
                        "        \"size\": \"string\"\n" +
                        "        \"type\": \"string\"\n" +
                    "        },\n" +
                    "        {\n" +
                        "        \"id\": image_id_of_the_product,\n" +
                        "        \"url\": \"string\"\n" +
                        "        \"size\": \"string\"\n" +
                        "        \"type\": \"string\"\n" +
                    "        }\n" +
                    "    ]\n" +
                    "}"))),
            @ApiResponse(responseCode = "500", description = "No product with this id", content = @Content(schema = @Schema(example = " ")))
    })
    @GetMapping(value = "/product/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@Parameter(description = "ID of the product") @PathVariable("id") Long id) {
        String dele = "0";
        Product product = productService.findProductById(id, dele);

        if (product == null) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @Operation(summary = "Find products as a list", description = "Lists all products", tags = { "Product" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(schema = @Schema(example = "{\n" +
                            "    \"_embedded\": {\n" +
                            "        \"products\": [\n" +
                            "    \"id\": product_id,\n" +
                            "    \"name\": \"string\",\n" +
                            "    \"shortdescription\": \"string\",\n" +
                            "    \"longdescription\": \"string\",\n" +
                            "    \"price\": float,\n" +
                            "    \"quantiry\": int,\n" +
                            "    \"createdDate\": string,\n" +
                            "    \"modifiedDate\": string,\n" +
                            "    \"category\": [\n id_1, id_2" +
                            "    ],\n" +
                            "    \"subcategory\": [\n id_1, id_2" +
                            "    ],\n" +
                            "    \"coverImage\": {\n" +
                            "        \"id\": cover_image_id_of_the_product,\n" +
                            "        \"url\": \"string\"\n" +
                            "        \"size\": \"string\"\n" +
                            "        \"type\": \"string\"\n" +
                            "    },\n" +
                            "    \"customprices\": string,\n" +
                            "    \"unitAreaPrice\": string,\n" +
                            "    \"itemSet\": [\n" +
                            "        {\n" +
                            "            \"id\": id_item,\n" +
                            "            \"width\": int\n" +
                            "            \"length\": int\n" +
                            "            \"height\": int\n" +
                            "            \"price\": Double\n" +
                            "        },\n" +
                            "        {\n" +
                            "            \"id\": id_item,\n" +
                            "            \"width\": int\n" +
                            "            \"length\": int\n" +
                            "            \"height\": int\n" +
                            "            \"price\": Double\n" +
                            "        }\n" +
                            "    ]\n" +
                            "    \"media\": [\n" +
                            "        {\n" +
                                "        \"id\": image_id_of_the_product,\n" +
                                "        \"url\": \"string\"\n" +
                                "        \"size\": \"string\"\n" +
                                "        \"type\": \"string\"\n" +
                            "        },\n" +
                            "        {\n" +
                                "        \"id\": image_id_of_the_product,\n" +
                                "        \"url\": \"string\"\n" +
                                "        \"size\": \"string\"\n" +
                                "        \"type\": \"string\"\n" +
                            "        }\n" +
                            "    ]\n" +
                            "                \"_links\": {\n" +
                            "                    \"products\": {\n" +
                            "                        \"href\": \"http://localhost:8080/product/product/{id}\"\n" +
                            "                    }\n" +
                            "                }\n" +
                            "            },\n"+
                            "        ]\n" +
                            "    },\n" +
                            "    \"_links\": {\n" +
                            "        \"self\": {\n" +
                            "            \"href\": \"http://localhost:8080/product/products\"\n" +
                            "        }\n" +
                            "    }\n" +
                            "}")
                    )
            )
    })
    @GetMapping(value="/Allproducts", headers="Accept=application/json")
    public ResponseEntity<CollectionModel<Product>> getAllProducts() {

        String dele = "0";
        List<Product> products = productService.getAllProduct(dele);

        return ResponseEntity.ok(
                new CollectionModel<>(products,
                        linkTo(methodOn(ProductServiceImpl.class).getAllProduct(dele)).slash("product").slash("products").withSelfRel()));
    }

}
