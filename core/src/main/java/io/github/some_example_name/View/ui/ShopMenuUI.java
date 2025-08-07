//public class ShopScreen implements Screen {
//    private final Stage stage;
//    private final Skin skin;
//    private final Shops shopType;
//    private final Seasons currentSeason;
//    private boolean showOnlyAvailable = false;
//
//    public ShopScreen(Shops shopType, Seasons currentSeason) {
//        this.shopType = shopType;
//        this.currentSeason = currentSeason;
//        this.skin = new Skin(Gdx.files.internal("uiskin.json"));
//        this.stage = new Stage();
//
//        setupUI();
//    }
//
//    private void setupUI() {
//        Table mainTable = new Table();
//        mainTable.setFillParent(true);
//        stage.addActor(mainTable);
//
//        // Product filter
//        TextButton filterButton = new TextButton(showOnlyAvailable ? "Show All" : "Available Only", skin);
//        filterButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                showOnlyAvailable = !showOnlyAvailable;
//                setupUI(); // Rebuild UI with new filter
//            }
//        });
//        mainTable.add(filterButton).colspan(2).padBottom(20).row();
//
//        // Product list
//        Table productsTable = new Table();
//        ScrollPane scrollPane = new ScrollPane(productsTable, skin);
//        scrollPane.setScrollingDisabled(true, false);
//
//        List<MaterialInShop> materials = shopType.getMaterials();
//        for (MaterialInShop item : materials) {
//            // Apply filter
//            if (showOnlyAvailable && !isAvailable(item)) continue;
//
//            // Text color based on availability
//            Label.LabelStyle style = new Label.LabelStyle();
//            style.font = skin.getFont("default-font");
//            style.fontColor = isAvailable(item) ? Color.WHITE : Color.DARK_GRAY;
//
//            Label nameLabel = new Label(item.getMaterial().getName(), style);
//            Label priceLabel = new Label(String.valueOf(getCurrentPrice(item)), style);
//
//            productsTable.add(nameLabel).pad(10).left();
//            productsTable.add(priceLabel).pad(10).right();
//            productsTable.row();
//
//            // Add click functionality for available products
//            if (isAvailable(item)) {
//                nameLabel.addListener(new ClickListener() {
//                    @Override
//                    public void clicked(InputEvent event, float x, float y) {
//                        showPurchaseDialog(item);
//                    }
//                });
//            }
//        }
//
//        mainTable.add(scrollPane).colspan(2).grow().row();
//    }
//
//    private boolean isAvailable(MaterialInShop item) {
//        return item.getSeasons() == null || item.getSeasons() == currentSeason;
//    }
//
//    private int getCurrentPrice(MaterialInShop item) {
//        return isAvailable(item) ? item.getOrdinaryPrice() : item.getOutOfSeasonPrice();
//    }
//
//    private void showPurchaseDialog(MaterialInShop item) {
//        Dialog dialog = new Dialog("Purchase", skin);
//        dialog.getContentTable().defaults().pad(10);
//
//        // Display product info
//        dialog.getContentTable().add(new Label("Item: " + item.getMaterial().getName(), skin)).row();
//        dialog.getContentTable().add(new Label("Price: " + item.getOrdinaryPrice(), skin)).row();
//
//        // Quantity control
//        final int[] quantity = {1};
//        Label quantityLabel = new Label("Quantity: " + quantity[0], skin);
//
//        TextButton increaseBtn = new TextButton("+", skin);
//        increaseBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                quantity[0]++;
//                quantityLabel.setText("Quantity: " + quantity[0]);
//            }
//        });
//
//        TextButton decreaseBtn = new TextButton("-", skin);
//        decreaseBtn.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                if (quantity[0] > 1) {
//                    quantity[0]--;
//                    quantityLabel.setText("Quantity: " + quantity[0]);
//                }
//            }
//        });
//
//        Table quantityTable = new Table();
//        quantityTable.add(decreaseBtn);
//        quantityTable.add(quantityLabel);
//        quantityTable.add(increaseBtn);
//        dialog.getContentTable().add(quantityTable).row();
//
//        // Confirm/Cancel buttons
//        TextButton buyButton = new TextButton("Buy", skin);
//        buyButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                // Implement purchase logic here
//                dialog.hide();
//            }
//        });
//
//        TextButton cancelButton = new TextButton("Cancel", skin);
//        cancelButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                dialog.hide();
//            }
//        });
//
//        dialog.getButtonTable().add(buyButton);
//        dialog.getButtonTable().add(cancelButton);
//        dialog.show(stage);
//    }
//
//    @Override
//    public void render(float delta) {
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        stage.act(delta);
//        stage.draw();
//    }
//
//    // Other required Screen methods
//    @Override public void show() { Gdx.input.setInputProcessor(stage); }
//    @Override public void resize(int width, int height) { stage.getViewport().update(width, height, true); }
//    @Override public void pause() {}
//    @Override public void resume() {}
//    @Override public void hide() {}
//    @Override public void dispose() { stage.dispose(); }
//}
