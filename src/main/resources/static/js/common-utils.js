// Cache for what was selected by the autocomplete widget
var resourceName;
// The identifier of the div which defines this dialog
var dialogWidgetId;
var autocompleteWidgetId;
var itemsQueueWidgetId;
// Hidden widget that holds a comma-separated list of items in queue as value
var itemsListWidgetId; 
var selectionCache;
var selectedItems = [];

$( ".dialog-with-autocomplete-widget").dialog({ 
	autoOpen: false,
	resizable: false,
    height: "auto",
    width: 600,
    modal: true,
    classes: {
          "ui-dialog": "dialog-theme",
          "ui-dialog-titlebar": "no-close"
      },
    buttons: [
    	{
            id: "add-button",
            text: "Add",
            click: function() { 
            	$( this ).dialog( "close" );
            	processSelection();
            }
        },
        {
            id: "cancel-button",
            text: "Cancel",
            click: function() { 
            	$( this ).dialog( "close" );
            }
        }
    ]
	});

function showAutocompleteDialog(dialog, resource, itemsQueue, selectedItemsList) {
  dialogWidgetId = dialog;
  resourceName = resource;
  itemsQueueWidgetId = itemsQueue;
  itemsListWidgetId = selectedItemsList;

	$( "#" + dialogWidgetId ).dialog( "open" );
	// Disable the Add button
	toogleEnableOrDisableAddButton(true);
} 

$( ".autocomplete" ).autocomplete({
	source: function(request, response) {
      $.ajax({
        url: "/safebusiness/api/v1/" + resourceName,
        dataType: "json",
        success: function( data ) {
            response($.map(data, function (item) {
              if (resourceName === "article") {
            	  return {
                      label: item.description,
                      value: item.articleNumber
                  };
              }
              return {
                  label: item.name,
                  value: item.name
              };
        }));
          }
      });
  },
  select: function( event, selection ) {
	  selectionCache =  selection;
	  // Enable the Add button
	  toogleEnableOrDisableAddButton(false);
    }
});

function toogleEnableOrDisableAddButton(disable) {
	// Get the dialog buttons.
	var dialogButtons = $( "#" + dialogWidgetId ).dialog("option", "buttons");

	// Find and disable the "Done" button.
	$.each(dialogButtons, function (buttonIndex, button) {
	    if (button.id === "add-button") {
	        button.disabled = disable;
	    }
	})

	// Update the dialog buttons.
	$("#" + dialogWidgetId).dialog("option", "buttons", dialogButtons);
}

function processSelection() {
	if (!selectionCache) {
		return;
	}
	// First check whether this exists in queue
	if (selectedItems.includes(selectionCache.item.label)) {
		return;
	}
	if (resourceName === "article") {
		if (selectedItems.includes(selectionCache.item.value)) {
			return;
		}
		selectedItems.push(selectionCache.item.value);
		$("#" + itemsQueueWidgetId).append("<li>" + selectionCache.item.value + "</li>");
		$("#" + itemsListWidgetId).val(listToCommaSeparatedString(selectedItems));
		return;
	}
	selectedItems.push(selectionCache.item.label);
	$("#" + itemsQueueWidgetId).append("<li>" + selectionCache.item.label + "</li>");
	$("#" + itemsListWidgetId).val(listToCommaSeparatedString(selectedItems));
}

function listToCommaSeparatedString(list) {
	var commaSeparatedString = "";
	for (i = 0; i < list.length; i++) {
		if (i === 0) {
			commaSeparatedString = commaSeparatedString.concat(list[i]);
			continue;
		}
		commaSeparatedString = commaSeparatedString.concat("," + list[i]);
	}
	return commaSeparatedString;
}